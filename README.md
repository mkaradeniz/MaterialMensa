# MaterialMensa
MaterialMensa is a cafeteria app for the Paderborn University.

Get it on the [Play Store](https://play.google.com/store/apps/details?id=de.prttstft.materialmensa).

## Development

### API
* You need a personal API-Key issued by the [Studentenwerk Paderborn](http://www.studentenwerk-pb.de).

### Firebase
* MaterialMensa uses two [Firebase](https://firebase.google.com) projects, one for debug and one for release builds.
* Set up your Firebase projects according to the [docs](https://firebase.google.com/docs/android/setup).
* In the debug project add an Android app with the package name ``de.prttstft.materialmensa.debug`` and in the release project``de.prttstft.materialmensa``.
 Create two folders "debug" and "release" inside [app/src](app/src) and add your ``google-services.json`` into the respective folders.
* See [resources/rules](resources/rules) on how to compile and deploy database rules.

### Kotlin
MaterialMensas code is party written in [Kotlin](https://kotlinlang.org/). New code should be written in Kotlin directly while old code may be converted in the future.

## Legacy Version
The legacy version without Firebase or Google Play Services dependencies can be found [here](https://github.com/prttstft/MaterialMensa/tree/legacy).

## Acknowledgements
- [AboutLibraries](https://github.com/mikepenz/AboutLibraries)
- [ButterKnife](https://github.com/JakeWharton/butterknife)
- [CircleImageView](https://github.com/hdodenhof/CircleImageView)
- [EmojiOne](http://emojione.com/)
- [Firebase](https://firebase.google.com)
- [Glide](https://github.com/bumptech/glide)
- [Gson](https://github.com/google/gson)
- [Icons8](https://icons8.com)
- [joda-time-android](https://github.com/dlew/joda-time-android)
- [Kotlin](https://kotlinlang.org/)
- [multiline-collapsingtoolbar](https://github.com/opacapp/multiline-collapsingtoolbar)
- [Retrofit](https://github.com/square/retrofit)
- [RxAndroid](https://github.com/ReactiveX/RxAndroid)
- [Studentenwerk Paderborn](http://www.studentenwerk-pb.de/)
- [Timber](https://github.com/JakeWharton/timber)