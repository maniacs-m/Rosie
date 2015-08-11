/*
 * The MIT License (MIT) Copyright (c) 2014 karumi Permission is hereby granted, free of charge,
 * to any person obtaining a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to
  * do so, subject to the following conditions: The above copyright notice and this permission
  * notice shall be included in all copies or substantial portions of the Software. THE SOFTWARE
  * IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
  * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
  * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
  * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.karumi.rosie.repository.datasource;

import com.karumi.rosie.UnitTest;
import com.karumi.rosie.doubles.AnyCacheableItem;
import com.karumi.rosie.repository.PaginatedCollection;
import com.karumi.rosie.time.TimeProvider;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;

public class InMemoryPaginatedDataSourceTest extends UnitTest {

  private static final long ANY_TTL = 10;

  @Mock private TimeProvider timeProvider;

  @Test public void shouldAddItemsBasedOnTheOffsetAndLimit() throws Exception {
    int offset = 0;
    int limit = 20;
    boolean hasMore = true;
    Collection<AnyCacheableItem> items = givenSomeItems(offset + limit);
    PaginatedDataSource<AnyCacheableItem> dataSource = givenAnInMemoryPaginatedDataSource();

    dataSource.addOrUpdate(offset, limit, items, hasMore);

    PaginatedCollection<AnyCacheableItem> page = dataSource.get(offset, limit);
    assertEquals(items, page.getItems());
  }

  @Test public void shouldReturnTheOffsetIHaveRequested() throws Exception {
    int offset = 0;
    int limit = 20;
    boolean hasMore = true;
    Collection<AnyCacheableItem> items = givenSomeItems(offset + limit);
    PaginatedDataSource<AnyCacheableItem> dataSource = givenAnInMemoryPaginatedDataSource();

    dataSource.addOrUpdate(offset, limit, items, hasMore);
    PaginatedCollection<AnyCacheableItem> page = dataSource.get(offset, limit);

    assertEquals(offset, page.getOffset());
  }

  @Test public void shouldReturnTheLimitIHaveRequested() throws Exception {
    int offset = 0;
    int limit = 20;
    boolean hasMore = true;
    Collection<AnyCacheableItem> items = givenSomeItems(offset + limit);
    PaginatedDataSource<AnyCacheableItem> dataSource = givenAnInMemoryPaginatedDataSource();

    dataSource.addOrUpdate(offset, limit, items, hasMore);
    PaginatedCollection<AnyCacheableItem> page = dataSource.get(offset, limit);

    assertEquals(limit, page.getLimit());
  }

  @Test public void shouldReturnHasMoreIfThereAreMoreItemsToLoad() throws Exception {
    int offset = 0;
    int limit = 20;
    boolean hasMore = true;
    Collection<AnyCacheableItem> items = givenSomeItems(offset + limit);
    PaginatedDataSource<AnyCacheableItem> dataSource = givenAnInMemoryPaginatedDataSource();

    dataSource.addOrUpdate(offset, limit, items, hasMore);
    PaginatedCollection<AnyCacheableItem> page = dataSource.get(offset, limit);

    assertEquals(hasMore, page.hasMore());
  }

  private Collection<AnyCacheableItem> givenSomeItems(int numberOfItems) {
    List<AnyCacheableItem> items = new LinkedList<>();
    for (int i = 0; i < numberOfItems; i++) {
      AnyCacheableItem item = new AnyCacheableItem(String.valueOf(i));
      items.add(item);
    }
    return items;
  }

  private InMemoryPaginatedDataSource<AnyCacheableItem> givenAnInMemoryPaginatedDataSource() {
    return new InMemoryPaginatedDataSource<>(timeProvider, ANY_TTL);
  }
}