package com.example.chat_live

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.chat_live.Data.CHATS
import com.example.chat_live.Data.ChatData
import com.example.chat_live.Data.ChatUser
import com.example.chat_live.Data.Events
import com.example.chat_live.Data.MESSAGE
import com.example.chat_live.Data.Message
import com.example.chat_live.Data.STATUS
import com.example.chat_live.Data.Status
import com.example.chat_live.Data.USER_NODE
import com.example.chat_live.Data.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.tasks.await
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.util.Calendar
import java.util.UUID
import javax.inject.Inject
val xrayImg = mutableStateOf<String?>(null)
val xrayReport = mutableStateOf<String?>(null)
@HiltViewModel
class CLViewModel @Inject constructor(
    val auth: FirebaseAuth, val db: FirebaseFirestore, val storage: FirebaseStorage
) : ViewModel() {


    val inProcess = mutableStateOf(false)
    val eventMutableState = mutableStateOf<Events<String>?>(null)
    val signIN = mutableStateOf(false)

    val userData = mutableStateOf<UserData?>(null)



    val inProcessChats = mutableStateOf(false)
    val chats = mutableStateOf<List<ChatData>>(listOf())
    val chatMessage = mutableStateOf<List<Message>>(listOf())
    val inProgressChatMessage = mutableStateOf(false)
    var currentChatMessageListener: ListenerRegistration? = null
    val status = mutableStateOf<List<Status>>(listOf())
    val inprogressStatus = mutableStateOf(false)
    val inprogressPasswordreset = mutableStateOf(false)

    init {

        val currentUser = auth.currentUser
        signIN.value = currentUser != null
        currentUser?.uid?.let {
            getUserData(it)
        }
    }

    suspend fun passwordReset(email: String): Boolean {
        inprogressPasswordreset.value = true
        val deferredResult = CompletableDeferred<Boolean>()

        try {
            auth.sendPasswordResetEmail(email).await()
            Log.e("Chat-Live Application", "Email sent.")
            deferredResult.complete(true)
        } catch (exception: Exception) {
            handleException(exception, custom = "Failed")
            deferredResult.complete(false)
        } finally {
            inprogressPasswordreset.value = false
        }

        return deferredResult.await()
    }
    fun populateMessage(chatId: String) {
        inProgressChatMessage.value = true
        currentChatMessageListener =
            db.collection(CHATS).document(chatId).collection(MESSAGE)
                .addSnapshotListener { value, error ->
                    if (error != null) {
                        handleException(error)


                    }
                    if (value != null) {
                        chatMessage.value = value.documents.mapNotNull {
                            it.toObject<Message>()
                        }.sortedBy { it.timestamp }
                        inProgressChatMessage.value = false
                    }
                }
    }

    fun depopulatedMessage() {
        chatMessage.value = listOf()
        currentChatMessageListener = null

    }

    fun populateChats() {
        inProcessChats.value = true
        db.collection(CHATS).where(
            Filter.or(
                Filter.equalTo("user1.userId", userData.value?.userId),
                Filter.equalTo("user2.userId", userData.value?.userId)

            )
        ).addSnapshotListener { value, error ->
            if (error != null) {
                handleException(error)


            }
            if (value != null) {
                chats.value = value.documents.mapNotNull {
                    it.toObject<ChatData>()
                }
                inProcessChats.value = false
            }
        }


    }

    fun SignUps(
        navController: NavController, name: String, number: String, email: String, password: String
    ) {
        if (name.isEmpty() or number.isEmpty() or email.isEmpty() or password.isEmpty()) {
            handleException(custom = "Please fill all fields")
            return
        }
        inProcess.value = true
        Log.d("Chat-Live Application", "inProcess")
        db.collection("User").whereEqualTo("number", number).get().addOnSuccessListener {
            Log.d("Chat-Live Application", "inProcess1")
            if (it.isEmpty) {
                Log.d("Chat-Live Application", "signup user logged it")
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        signIN.value = true
                        Log.d("Chat-Live Application", "signup user logged it")
                        createOrUpdateProfile(name, number)
                        inProcess.value = false
                    } else {
                        handleException(it.exception, custom = "Failed")
                    }
                }
            } else {
                handleException(custom = "Number already exist")
                inProcess.value = false
            }
        }

    }

    fun LoginIN(number: String, password: String) {
        if (number.isEmpty() or password.isEmpty()) {
            handleException(custom = "FIll the Both Fields")
            return
        } else {
            inProcess.value = true
            auth.signInWithEmailAndPassword(number, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    signIN.value = true
                    inProcess.value = false
                    auth.currentUser?.uid?.let {
                        getUserData(it)
                    }
                } else {
                    handleException(exception = it.exception, custom = "login failed")
                }
            }
        }

    }

    fun createOrUpdateProfile(
        name: String? = null, number: String? = null, imageUrl: String? = null
    ) {
        val uid = auth.currentUser?.uid
        val userData = UserData(
            userId = uid,
            name = name ?: userData.value?.name,
            number = number ?: userData.value?.number,
            imageUrl = imageUrl ?: userData.value?.imageUrl
        )

        uid?.let { userId ->
            inProcess.value = true
            val userDocRef = db.collection(USER_NODE).document(userId)

            userDocRef.get().addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    // Update user data
                    userDocRef.set(userData, SetOptions.merge())
                        .addOnSuccessListener {
                            inProcess.value = false
                        }
                        .addOnFailureListener { exception ->
                            inProcess.value = false
                            handleException(exception, "Failed to update user data")
                        }
                } else {
                    // Create new user data
                    userDocRef.set(userData)
                        .addOnSuccessListener {
                            inProcess.value = false
                            getUserData(userId)
                        }
                        .addOnFailureListener { exception ->
                            inProcess.value = false
                            handleException(exception, "Failed to create user data")
                        }
                }
            }.addOnFailureListener { exception ->
                inProcess.value = false
                handleException(exception, "Cannot retrieve user data")
            }
        }
    }


    fun uploadProfileImage(uri: Uri) {
        uploadImage(uri) {

            createOrUpdateProfile(imageUrl=it.toString())

        }

    }

    fun uploadImage(uri: Uri, onSuccess: (Uri) -> Unit) {
        inProcess.value = true // Presumably, this is a MutableState or similar.
        inprogressStatus.value = true
        val storageRef = storage.reference
        val uuid = UUID.randomUUID() // Corrected typo from "uiid" to "uuid"
        val imageRef = storageRef.child("xrays/$uuid")
        val uploadTask = imageRef.putFile(uri)

        uploadTask.addOnSuccessListener {
            it.metadata?.reference?.downloadUrl?.addOnSuccessListener { uri ->
                onSuccess(uri)
                inProcess.value = false
                inprogressStatus.value = false
            }?.addOnFailureListener { exception ->
                handleException(exception, custom = "Image retrieve failed")
                inProcess.value = false
                inprogressStatus.value = false // Ensure consistency in variable naming: "inProcess" vs "inprogressStatus"
            }
        }.addOnFailureListener { exception ->
            handleException(exception, custom = "Image upload failed") // Improved custom message
            inProcess.value = false
            inprogressStatus.value = false
        }

    }


    fun onSendReply(chatId: String, message: String) {
        val time = Calendar.getInstance().time.toString()
        val msg = Message(userData.value?.userId, message, time)

        db.collection(CHATS).document(chatId).collection(MESSAGE).document().set(msg)

    }

    fun getUserData(uid: String) {

        inProcess.value = true
        db.collection(USER_NODE).document(uid).addSnapshotListener { value, error ->
            if (error != null) {
                handleException(error, "not retrive the user")
            }
            if (value != null) {
                var user = value.toObject<UserData>()
                userData.value = user
                inProcess.value = false
                populateChats()
                populateStatus()


            }
        }
    }

    fun handleException(exception: Exception? = null, custom: String = "") {

        Log.e("Chat-Live Application", "Chat Live Exception: $custom ", exception)
        exception?.printStackTrace()
        val errorMsg = exception?.localizedMessage ?: ""
        val message = if (custom.isEmpty()) errorMsg else custom

        eventMutableState.value = Events(message)
        inProcess.value = false


    }

    fun logout() {
        auth.signOut()
        signIN.value = false
        userData.value = null
        depopulatedMessage()
        currentChatMessageListener = null

        eventMutableState.value = Events("Logged Out")
    }

    fun onAddChat(number: String) {
        if (number.isEmpty() or !number.isDigitsOnly()) {
            handleException(custom = "Number must be contain digits ony")
        } else {
            db.collection(CHATS).where(
                Filter.or(
                    Filter.and(
                        Filter.equalTo("user1.number", number),
                        Filter.equalTo("user2.number", userData.value?.number)
                    ), Filter.and(
                        Filter.equalTo("user2.number", userData.value?.number),
                        Filter.equalTo("user1.number", number)
                    )


                )
            ).get().addOnSuccessListener {
                if (it.isEmpty) {
                    db.collection(USER_NODE).whereEqualTo("number", number).get()
                        .addOnSuccessListener {


                            if (it.isEmpty) {
                                handleException(custom = "Number Not Found")

                            } else {
                                val chatPartner = it.toObjects<UserData>()[0]
                                val id = db.collection(CHATS).document().id
                                val chat = ChatData(
                                    chatId = id, ChatUser(
                                        userData.value?.userId,
                                        userData.value?.name,
                                        userData.value?.imageUrl,
                                        userData.value?.number
                                    ), ChatUser(
                                        chatPartner.userId,
                                        chatPartner.name,
                                        chatPartner.imageUrl,
                                        chatPartner.number
                                    )

                                )

                                db.collection(CHATS).document(id).set(chat)
                            }
                        }
                        .addOnFailureListener {
                            handleException(it)
                        }
                } else {
                    handleException(custom = "chat already exists")
                }

            }
        }

    }

    fun uploadStatus(uri: Uri) {

            uploadImage(uri) {it->
                xrayImg.value = it.toString()
                Log.e("Chat-Live Application",it.toString())
                val okHttp= OkHttpClient()

                val json = "{\"text\": \"${it.toString()}\"}"  // Create JSON string with the URI
                val requestBody = json.toRequestBody("application/json".toMediaTypeOrNull())


                val request= Request.Builder()
                    .url("https://2797-34-125-83-26.ngrok-free.app/post")
                    .post(requestBody)
                    .build()
                okHttp.newCall(request).enqueue(ResponseHandler1())

                createStatus(it.toString())
        }
    }

    fun createStatus(imageUrl: String) {
        Log.e("Chat-Live Application", "Creating status with imageUrl: $imageUrl")

        // Ensure userData.value is not null before proceeding
        val user = userData.value
        if (user != null) {
            val newStatus = Status(
                ChatUser(
                    user.userId,
                    user.name,
                    user.imageUrl,
                    user.number
                ),
                imageUrl,
                System.currentTimeMillis()
            )

            db.collection(STATUS)
                .document()
                .set(newStatus)
                .addOnSuccessListener {
                    Log.d("Chat-Live Application", "Status successfully created.")
                }
                .addOnFailureListener { e ->
                    Log.e("Chat-Live Application", "Error creating status", e)
                }
        } else {
            Log.e("Chat-Live Application", "User data is null, cannot create status.")
        }
    }

    fun getMethod(){
        val okHttp = OkHttpClient()

        // Create GET Request
        val request = Request.Builder()
            .url("https://2797-34-125-83-26.ngrok-free.app/get")
            .build()

        // Enqueue the request with ResponseHandler
        okHttp.newCall(request).enqueue(ResponseHandler())

    }
    class ResponseHandler : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.e("Chat-Live Application", "Request Failed: ${e.message}")
        }

        override fun onResponse(call: Call, response: Response) {
            if (response.isSuccessful) {
                val responseBody = response.body?.string()
                xrayImg.value=responseBody
                responseBody?.let {
                    val json = JSONObject(it)
                    val imageUrl = json.getString("text")
                    xrayReport.value = imageUrl
                }
                Log.d("Chat-Live Application", xrayImg.value ?: "Response body is null")
            } else {
                Log.e("Chat-Live Application", "Request not successful: ${response.message}")
            }
        }
    }
    fun populateStatus() {
        val timeDella=24L*60*60*1000

        val cutofftime=System.currentTimeMillis()-timeDella


        db.collection(CHATS).where(
            Filter.or(
                Filter.equalTo("user1.userId", userData.value?.userId),
                Filter.equalTo("user2.userId", userData.value?.userId)

            )
        ).addSnapshotListener {

                              value, error ->
            inprogressStatus.value = true
                if (error != null) {
                    handleException(error)
                }

                if (value != null) {
                    val currentConnections = arrayListOf(userData.value?.userId)

                    val chats = value.toObjects<ChatData>()
                    chats.forEach { chat ->

                        if (chat.user1.userId == userData.value?.userId) {
                            currentConnections.add(chat.user2.userId)
                        } else {
                            currentConnections.add(chat.user1.userId)
                        }
                    }

                    db.collection(STATUS).whereGreaterThan("timestamp",cutofftime).whereIn("user.userId", currentConnections)
                        .addSnapshotListener { value, error ->

                                if (error != null) {
                                    handleException(error)
                                    inprogressStatus.value = true

                                }
                                if (value != null) {
                                    status.value=value.toObjects()
                                    inprogressStatus.value=false
                                }


                        }



            }
        }
    }

}
class ResponseHandler1(): Callback {
    override fun onResponse(call: Call, response: Response) {
        response.isSuccessful
        Log.d("Chat-Live Application",response.body?.string()+response.isSuccessful?:"not found")

    }

    override fun onFailure(call: Call, e: IOException) {

    }
    // Define ResponseHandler class implementing Callback

}

