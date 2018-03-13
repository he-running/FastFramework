/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.he.presentation.base;


import org.simple.eventbus.EventBus;

/**
 * Created by hesh on 2018/2/5.
 * Interface representing a BasePresenter in a model view presenter (MVP) pattern.
 */
public abstract class BasePresenter<V> {

	protected static String TAG;

	protected V view;

	public BasePresenter() {
		TAG = getClass().getSimpleName();
	}

	public void setView(V view) {
		this.view = view;
	}

	public void create() {
		EventBus.getDefault().register(this);
	}

	/**
	 * Method that control the lifecycle of the view. It should be called in the
	 * view's (Activity or Fragment) onResume() method.
	 */
	public abstract void resume();

	/**
	 * Method that control the lifecycle of the view. It should be called in the
	 * view's (Activity or Fragment) onPause() method.
	 */
	public abstract void pause();

	/**
	 * Method that control the lifecycle of the view. It should be called in the
	 * view's (Activity or Fragment) onDestroy() method.
	 */
	public void destroy() {
		EventBus.getDefault().unregister(this);
	}
}
