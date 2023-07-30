# ShoppingListApp

Приложение "Список покупок" с реализованными функциями по добавлению, чтению, редактированию и удалению каждого из элементов списка.

**Технологии:**
- Kotlin
- Coroutines
- Room
- Dagger2
- Preference

**Дополнительно:**
- LiveData
- ViewBinding
- Git
- Xml(разметка)

**Зависимости:**

    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4"
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4"
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1"
    implementation "androidx.lifecycle:lifecycle-runtime-ktx:2.6.1"

    implementation "androidx.room:room-runtime:2.5.2"
    implementation "androidx.room:room-ktx:2.5.2"
    kapt "androidx.room:room-compiler:2.5.2"

    implementation 'com.google.dagger:dagger:2.35.1'
    kapt 'com.google.dagger:dagger-compiler:2.35.1'

    implementation 'androidx.preference:preference-ktx:1.2.0'
    implementation 'androidx.preference:preference:1.2.0'
