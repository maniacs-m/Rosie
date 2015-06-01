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

package com.karumi.rosie.demo.hipsterlist.view.activity.phone;

import android.os.Bundle;
import android.widget.ListView;
import butterknife.InjectView;
import butterknife.OnItemClick;
import com.karumi.rosie.demo.R;
import com.karumi.rosie.demo.base.view.transformation.RoundAvatarTransformation;
import com.karumi.rosie.demo.hipsterdetail.view.activity.HipsterDetailActivity;
import com.karumi.rosie.demo.hipsterlist.domain.HipsterListDomainModule;
import com.karumi.rosie.demo.hipsterlist.view.HipsterListViewModule;
import com.karumi.rosie.demo.hipsterlist.view.adapter.HipstersAdapter;
import com.karumi.rosie.demo.hipsterlist.view.model.Hipster;
import com.karumi.rosie.demo.hipsterlist.view.presenter.HipsterListPresenter;
import com.karumi.rosie.view.activity.RosieActivity;
import com.karumi.rosie.view.presenter.annotation.Presenter;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;

/**
 * RosieActivity extension created to show a List of Hipster instances into a ListView.
 */
public class HipsterListActivity extends RosieActivity implements HipsterListPresenter.View {

  @Inject @Presenter HipsterListPresenter presenter;
  @Inject Picasso picasso;
  private Transformation transformationAvatar;

  @InjectView(R.id.lv_feed) ListView listViewFeed;

  private HipstersAdapter hipstersAdapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_hipster_list);
    super.onCreate(savedInstanceState);
  }

  @Override public void updateList(List<Hipster> hipsters) {
    updateHipstersAdapter(hipsters);
  }

  @OnItemClick(R.id.lv_feed) void onHipsterClicked(int position) {
    Hipster hipster = hipstersAdapter.getItem(position);
    HipsterDetailActivity.open(this, hipster);
  }

  private void updateHipstersAdapter(List<Hipster> hipsters) {
    if (hipstersAdapter == null) {
      transformationAvatar = new RoundAvatarTransformation();
      hipstersAdapter =
          new HipstersAdapter(getBaseContext(), hipsters, picasso, transformationAvatar);
      listViewFeed.setAdapter(hipstersAdapter);
    } else {
      hipstersAdapter.notifyDataSetChanged();
    }
  }

  @Override protected List<Object> getActivityScopeModules() {
    return Arrays.asList(new HipsterListViewModule(), new HipsterListDomainModule());
  }
}