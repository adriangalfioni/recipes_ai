package agalfioni.recipesai.core.di

import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import org.koin.dsl.module

val firebaseModule = module {

    single { Firebase.analytics }
}