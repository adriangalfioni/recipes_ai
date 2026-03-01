package agalfioni.recipesai.core.di

import com.google.firebase.Firebase
import com.google.firebase.analytics.analytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import org.koin.dsl.module

val firebaseModule =
    module {

        single { Firebase.analytics }

        single {
            Firebase.firestore.also {
                FirebaseFirestore.setLoggingEnabled(true)
            }
        }
    }
