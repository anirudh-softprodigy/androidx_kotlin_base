package com.kuteapp.mvp

/**
 * Created by Anirudh_Sharma on 01-May-18.
 */
interface BaseMvpPresenter<in V : BaseMvpView> {

    fun attachView(view: V)

    fun detachView()
}