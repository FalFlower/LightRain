package com.lightrain.android.presenter.impl

import com.lightrain.android.presenter.interf.ClassificationPresenter
import com.lightrain.android.view.ClassificationView

class ClassificationPresenterImpl(private var view:ClassificationView) :ClassificationPresenter {
    companion object{

    }

    override fun loadData() {
        view.onSuccess(0,"")
    }

}