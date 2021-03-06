package com.marmelade.android.spacex.logic.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass


/**
 * @author Petr Tykal <tykal.pete@gmail.com>
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)