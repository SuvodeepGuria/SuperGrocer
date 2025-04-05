package com.suvodeep.supergrocer

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.suvodeep.supergrocer.data.InternetItem
import com.suvodeep.supergrocer.internet.Api
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

val database = Firebase.database
val cartRef = database.getReference("SuperGrocer User ${auth.currentUser?.uid}/cart")
class SuperGrocerViewModel: ViewModel()/*(application: Application) : AndroidViewModel(application)*/ {
    private val _uiState = MutableStateFlow(SuperGrocerUiState())
    val uiState: StateFlow<SuperGrocerUiState> = _uiState.asStateFlow()

    private val _isVisible=MutableStateFlow(true)
    val isVisible=_isVisible.asStateFlow()

    var itemUiState:DownloadItemState by mutableStateOf(DownloadItemState.Loading)
        private set

    private lateinit var internetJob: Job
    private var screenJob: Job

    private val _cartItem=MutableStateFlow<List<InternetItem>>(emptyList())
    val  cartItem:StateFlow<List<InternetItem>> get() = _cartItem.asStateFlow()

    private val _selectedAddress=MutableStateFlow("")
    val selectedAddress: StateFlow<String> get() = _selectedAddress

    private val _orderItems = MutableStateFlow<List<Map<String, Any>>>(emptyList())
    val orderItems: StateFlow<List<Map<String, Any>>> get() = _orderItems

    private val _grandTotal= MutableStateFlow(0)
    val grandTotal:StateFlow<Int> get() = _grandTotal

//    private val _quantity= MutableStateFlow(0)
//    fun getQuantity(qty:Int){
//        _quantity.value=qty
//    }


//    private val _getSavedCartItems=MutableStateFlow<List<InternetItem>>(emptyList())
//    val getSavedCartItems:StateFlow<List<InternetItem>> get() = _getSavedCartItems.asStateFlow()
//    fun getSavedCartItems(){
//        viewModelScope.launch {
//            _getSavedCartItems.value=cartItem.value
//        }
//    }

//    private val _addedToCart= MutableStateFlow(0)
//    val addedToCart:StateFlow<Int> get() = _addedToCart.asStateFlow()

    private val _address= MutableStateFlow<Set<String>>(mutableSetOf(""))
    val address:StateFlow<Set<String>> get() = _address.asStateFlow()

    private val _user=MutableStateFlow<FirebaseUser?>(null)
    val user:StateFlow<FirebaseUser?> get() = _user.asStateFlow()

    private val _phoneNo=MutableStateFlow("")
    val phoneNo:StateFlow<String> get() = _phoneNo/*.asStateFlow()*/

    private val _otp= MutableStateFlow("")
    val otp:StateFlow<String> get() = _otp.asStateFlow()

    private val _verificationId= MutableStateFlow("")
    val verificationId:StateFlow<String> get() = _verificationId

    private val _timer=MutableStateFlow(60L)
    val timer:StateFlow<Long> get() = _timer

    private lateinit var timerJob: Job

    private val _onclickLogOut= MutableStateFlow(false)
    val onclickLogOut:StateFlow<Boolean> get() = _onclickLogOut

//    val database = Firebase.database
//    val cartRef = database.getReference("SuperGrocer User ${auth.currentUser?.uid}/cart")
    val addressRef = database.getReference("SuperGrocer User ${auth.currentUser?.uid}/address")
    val orderRef=database.getReference("SuperGrocer User ${auth.currentUser?.uid}/order")

//    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "cart_and_address")
//    private val cartItemKey = stringPreferencesKey("cart_item")
//    private val addressKey = stringPreferencesKey("address")

//    @SuppressLint("StaticFieldLeak")
//    private val context=application.applicationContext

    fun getGrandTotal(grandTotal:Int){
        _grandTotal.value=grandTotal
    }

    fun setSelectedAddressFromOrderScreen(address:String){
        _selectedAddress.value=address
    }

    fun updateText(updateText: String) {
        _uiState.update {
            it.copy(
                clickableState = updateText
            )
        }
    }

//    private suspend fun saveAddressToDataStore() {
//        context.dataStore.edit { preferences ->
//            preferences[addressKey] = Json.encodeToString(_address.value.toList())
//        }
//    }

//    private suspend fun loadAddressFromDataStore() {
//        val fullDataOfAddress = context.dataStore.data.first()
//        val addressJson = fullDataOfAddress[addressKey]
//        if (!addressJson.isNullOrBlank()) {
//            _address.value = Json.decodeFromString<List<String>>(addressJson).toMutableSet()
//        }
//    }

    fun addAddress(updateAddress: String) {
        _address.update { (it + updateAddress).toMutableSet() }
        viewModelScope.launch {
//            saveAddressToDataStore()
        }
    }

    fun addAddressToDatabase(addAddress: String) {
        addressRef.push().setValue(addAddress)
    }

    fun readAddressFromDatabase(){
        addressRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                val value = dataSnapshot.getValue<String>()
//                Log.d(TAG, "Value is: $value")
                _address.value=emptySet()
                for(childSnapShot in dataSnapshot.children){
                    val item=childSnapShot.value
                    item?.let{
                        val address=it
                        addAddress(address.toString())
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

    }

    fun removeAddress(removeAddress: String) {
//        _address.update { (it - removeAddress).toMutableSet() }
//        viewModelScope.launch {
//            saveAddressToDataStore()
//        }
        addressRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(childSnapShot in dataSnapshot.children){
                    val item=childSnapShot.value
                    item?.let{
                        if(removeAddress==it){
                            childSnapShot.ref.removeValue()
                        }
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    fun categoryClick(categoryId: Int){
        _uiState.update {
            it.copy(
                onCategoryState = categoryId
            )
        }
    }

//    fun getDiscount(discount:Int){
//        _discount.value=discount
//    }

//    fun addedToCart(addedToCart:Int){
//        _addedToCart.value=addedToCart
//    }

    private fun toggleVisibility(){
        _isVisible.value=false
    }

    sealed interface DownloadItemState{
        data class Success(val item:List<InternetItem>):DownloadItemState
        data object Loading:DownloadItemState
        data object Error:DownloadItemState
    }

    fun getInternetItems(){
        internetJob=viewModelScope.launch {
            try {
                val listResult = Api.retrofitService.getItems()
                itemUiState = DownloadItemState.Success(listResult)
//                loadCartItemFromDataStore()
//                loadAddressFromDataStore()
            }catch (e:Exception){
                Log.e("ItemError",e.message.toString())
                itemUiState=DownloadItemState.Error
                toggleVisibility()
                screenJob.cancel()
            }
        }
    }

//    private suspend fun loadCartItemFromDataStore(){
//        val fullDataOfCart=context.dataStore.data.first()
//        val cartItemJson=fullDataOfCart[cartItemKey]
//        if(!cartItemJson.isNullOrBlank()){
//            _cartItem.value= Json.decodeFromString(cartItemJson)
//        }
//    }
//    /*private suspend*/ suspend fun saveCartItemToDataStore(){
//        context.dataStore.edit {   preferences->
//            preferences[cartItemKey]= Json.encodeToString(_cartItem.value)
//        }
//    }

    fun addToCart(item: InternetItem){
        _cartItem.value=_cartItem.value+item
        viewModelScope.launch {
//            saveCartItemToDataStore()
        }
    }

    fun addCartItemsToDatabase(item: InternetItem){
        cartRef.push().setValue(item)
    }

    fun readItemsFromDatabase(){
        // Read from the database
        cartRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                val value = dataSnapshot.getValue<String>()
//                Log.d(TAG, "Value is: $value")
                _cartItem.value=emptyList()
                for(childSnapShot in dataSnapshot.children){
                    val item=childSnapShot.getValue(InternetItem::class.java)
                    item?.let{
                        val newCartItem=it
                        addToCart(newCartItem)
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    fun removeFromCart(removeItem: InternetItem){
//        _cartItem.value= _cartItem.value-item
//        viewModelScope.launch {
//            saveCartItemToDataStore()
//        }
        cartRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(childSnapShot in dataSnapshot.children){
                    val item=childSnapShot.getValue(InternetItem::class.java)
                    var itemRemove=false
                    item?.let{
                        if(removeItem.itemName==it.itemName && removeItem.itemCategoryId==it.itemCategoryId){
                            childSnapShot.ref.removeValue()
                            itemRemove=true
                        }
                    }
                    if(itemRemove) break

                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    fun removeAll(removeItem: InternetItem) {
//        _cartItem.value = _cartItem.value.filter { it.itemName != item.itemName }
//        viewModelScope.launch {
//            saveCartItemToDataStore()
//        }
        cartRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(childSnapShot in dataSnapshot.children){
                    val item=childSnapShot.getValue(InternetItem::class.java)
                    item?.let{
                        if(removeItem.itemName==it.itemName && removeItem.itemCategoryId==it.itemCategoryId){
                            childSnapShot.ref.removeValue()
                        }
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    fun cleanCart(){
        cartRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for(childSnapShot in dataSnapshot.children){
                    val item=childSnapShot.getValue(InternetItem::class.java)
                    item?.let{
                        childSnapShot.ref.removeValue()
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun updatePhoneNo(phoneNo:String){
        _phoneNo.value=phoneNo
    }

    fun setOTP(otp: String){
        _otp.value=otp
    }

    fun setVerificationId(verificationId: String){
        _verificationId.value=verificationId
    }

    fun setUser(user: FirebaseUser?){
        _user.value=user
    }

    fun clearData(){
        _user.value=null
        _phoneNo.value=""
        _otp.value=""
        _verificationId.value=""
        resetTimer()
    }

    fun startTimer(){
       timerJob= viewModelScope.launch {
            while (_timer.value>0){
                delay(1000)
                _timer.value--
            }
        }
    }

    fun resetTimer(){
        try{
            timerJob.cancel()
        }catch (e:Exception){

        }finally {
            _timer.value=60L
        }
    }

    fun setLogOutState(value: Boolean){
        _onclickLogOut.value=value
    }

    fun yourOrder(order: HashMap<String,Any>){
        orderRef.push().setValue(order)
//        orderRef.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                for(childSnapShot in dataSnapshot.children){
//                    val item=childSnapShot.getValue(InternetItem::class.java)
//                    item?.let{
//
//                    }
//
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Failed to read value
////                Log.w(TAG, "Failed to read value.", error.toException())
//            }
//        })
    }

    fun readOrdersFromDatabase() {
        orderRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val orderList = mutableListOf<Map<String, Any>>()
                for (child in snapshot.children) {
                    val order = child.value as? Map<String, Any>
                    order?.let {
                        orderList.add(it)
                    }
                }
                _orderItems.value = orderList.reversed() // 🔁 reverse the list
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Failed to load orders: ${error.message}")
            }
        })
    }

    init{
        screenJob=viewModelScope.launch {
            delay(3000)
            toggleVisibility()
//            loadAddressFromDataStore()
        }
        getInternetItems()
        readItemsFromDatabase()
        readAddressFromDatabase()
        readOrdersFromDatabase()
    }
}


