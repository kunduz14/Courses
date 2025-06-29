package com.kunduzamatbekova.android.courses.di

import android.content.Context
import androidx.room.Room
import com.kunduzamatbekova.android.core.adapter.CoursesAdapter
import com.kunduzamatbekova.android.data.model.Course
import com.kunduzamatbekova.android.data.CoursesDatabase
import com.kunduzamatbekova.android.data.CoursesRepository
import com.kunduzamatbekova.android.data.dao.CourseDao
import com.kunduzamatbekova.android.feature_favourites.FavouritesCoursesViewModel
import com.kunduzamatbekova.android.feature_home.HomeFragmentViewModel
import com.kunduzamatbekova.android.feature_login.LoginFragmentViewModel
import com.kunduzamatbekova.android.feature_login.api.UrlApi
import com.kunduzamatbekova.android.feature_login.service.UrlService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

val appModule = module {

    single {
        Room.databaseBuilder(
            get<Context>(),
            CoursesDatabase::class.java,
            "courses_database"
        ).build()
    }

    single<CourseDao> { get<CoursesDatabase>().courseDao() }

    single { CoursesRepository(get(), get()) }

    single {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        
        Retrofit.Builder()
            .baseUrl("https://ya.ru/")
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    single<UrlApi> { get<Retrofit>().create(UrlApi::class.java) }
    single { UrlService(get(), get()) }

    factory { (courses: List<Course>) ->
        CoursesAdapter(courses)
    }

    viewModel { LoginFragmentViewModel() }
    viewModel { HomeFragmentViewModel(get()) }
    viewModel { FavouritesCoursesViewModel(get()) }
} 