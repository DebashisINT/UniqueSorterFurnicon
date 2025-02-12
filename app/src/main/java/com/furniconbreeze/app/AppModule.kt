package com.furniconbreeze.app

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/*
 * Copyright (C) 2017 Naresh Gowd Idiga
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

@Module
class AppModule(val application: Application) {

    @Singleton
    @Provides
    fun provideApp(): Application {
        return application
    }

    @Singleton
    @Provides
    fun provideDb(): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "cleanapp.db").build()
    }


}