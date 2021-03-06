package com.unhappychoice.droidflyer.extension

import android.content.Context
import android.view.View
import com.unhappychoice.droidflyer.presentation.screen.core.Screen
import flow.Flow
import mortar.ViewPresenter

fun <V : View> ViewPresenter<V>.goTo(context: Context, screen: Screen) = Flow.get(context).set(screen)
fun <V : View> ViewPresenter<V>.goBack(context: Context) = Flow.get(context).goBack()

