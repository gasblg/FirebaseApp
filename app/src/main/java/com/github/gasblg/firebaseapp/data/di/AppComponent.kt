package com.github.gasblg.firebaseapp.data.di

import android.app.Application
import com.github.gasblg.firebaseapp.App
import com.github.gasblg.firebaseapp.analytics.di.AnalyticsModule
import com.github.gasblg.firebaseapp.analytics.di.CrashlyticsModule
import com.github.gasblg.firebaseapp.domain.di.FirebaseModule
import com.github.gasblg.firebaseapp.domain.di.InteractorModule
import com.github.gasblg.firebaseapp.domain.di.ManagersModule
import com.github.gasblg.firebaseapp.domain.di.UseCasesModule
import com.github.gasblg.firebaseapp.presentation.di.ActivityBuilder
import com.github.gasblg.firebaseapp.presentation.di.GoogleModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBuilder::class,
        GoogleModule::class,
        UseCasesModule::class,
        RepositoriesModule::class,
        FirebaseModule::class,
        AnalyticsModule::class,
        CrashlyticsModule::class,
        DateModule::class,
        ManagersModule::class,
        InteractorModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(app: App)
}


