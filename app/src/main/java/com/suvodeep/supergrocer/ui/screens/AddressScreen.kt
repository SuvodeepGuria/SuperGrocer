package com.suvodeep.supergrocer.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.suvodeep.supergrocer.SuperGrocerViewModel

//@Preview
@Composable
fun AddressScreen(superGrocerViewModel: SuperGrocerViewModel) {
    var fullName by remember { mutableStateOf("") }
//    var middleName by remember { mutableStateOf("") }
//    var lastName by remember { mutableStateOf("") }
    var phoneno by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var landmark by remember { mutableStateOf("") }
    var pincode by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }

    var fullNameError by remember { mutableStateOf(false) }
//    var middleNameError by remember { mutableStateOf(false) }
    var lastNameError by remember { mutableStateOf(false) }
    var phoneNoError by remember { mutableStateOf(false) }
    var addressError by remember { mutableStateOf(false) }
    var landmarkError by remember { mutableStateOf(false) }
    var pincodeError by remember { mutableStateOf(false) }
    var stateError by remember { mutableStateOf(false) }
    var cityError by remember { mutableStateOf(false) }

    val addresses by superGrocerViewModel.address.collectAsState()
    val addressList = addresses.toList()
    val addAddress=remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(addressList.size) { savedAddress ->
            var adrs=addressList[savedAddress]
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
//                    colors = CardDefaults.cardColors(Color(3,6,65,8))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(adrs, fontSize = 16.sp, fontWeight = FontWeight.Bold)
//                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment =Alignment.TopEnd
                        ) {
                            IconButton(onClick = { superGrocerViewModel.removeAddress(adrs) }) {
                                Icon(
                                    Icons.Outlined.Delete,
                                    contentDescription = "Delete Address",
//                                    tint = Color.Red
                                )
                            }
                        }
                    }
                }

        }
        item {
            if(addressList.isEmpty()){
                Text("No address is saved. Please add address.", modifier = Modifier.fillMaxWidth(),
                    fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }
        }
        item{
            if(!addAddress.value){
                Row(modifier = Modifier.width(200.dp).clickable(onClick = {
                    addAddress.value=!addAddress.value
                })) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Address")
                    Text("Add Address", fontWeight = FontWeight.Bold)
                }
            }else{
                Box{}
            }
        }
        if(addAddress.value) {
            item {
                OutlinedTextField(
                    value = fullName,
                    maxLines = 1,
                    onValueChange = {
                        fullName = it
                        fullNameError = it.isBlank()
                    },
                    label = { Text("Full Name*") },
                    isError = fullNameError,
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        if (fullNameError) Icon(Icons.Filled.Error, contentDescription = "Error")
                    }
                )
                if (fullNameError) Text("Full Name is required", color = Color.Red, fontSize = 12.sp)
            }
//            item {
//                OutlinedTextField(
//                    value = middleName,
//                    onValueChange = { middleName = it },
//                    label = { Text("Middle Name (Optional)") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//            }
//            item {
//                OutlinedTextField(
//                    value = lastName,
//                    onValueChange = {
//                        lastName = it
//                        lastNameError = it.isBlank()
//                    },
//                    label = { Text("Last Name*") },
//                    isError = lastNameError,
//                    modifier = Modifier.fillMaxWidth(),
//                    trailingIcon = {
//                        if (lastNameError) Icon(Icons.Filled.Error, contentDescription = "Error")
//                    }
//                )
//                if (lastNameError) Text("Last Name is required", color = Color.Red, fontSize = 12.sp)
//            }
            item {
                OutlinedTextField(
                    value = phoneno,
                    onValueChange = {
                        phoneno = it
                        phoneNoError = it.isBlank()
                    },
                    label = { Text("Phone No.*") },
                    isError = phoneNoError,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number),
                    trailingIcon = {
                        if (phoneNoError) Icon(Icons.Filled.Error, contentDescription = "Error")
                    }
                )
                if (phoneNoError) Text("Phone No. is required", color = Color.Red, fontSize = 12.sp)
            }
            item {
                OutlinedTextField(
                    value = address,
                    onValueChange = {
                        address = it
                        addressError = it.isBlank()
                    },
                    label = { Text("Address*") },
                    isError = addressError,
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        if (addressError) Icon(Icons.Filled.Error, contentDescription = "Error")
                    }
                )
                if (addressError) Text("Address is required", color = Color.Red, fontSize = 12.sp)
            }
            item {
                OutlinedTextField(
                    value = landmark,
                    onValueChange = { landmark = it
                        landmarkError=it.isBlank()},
                    label = { Text("Landmark*") },
                    isError = landmarkError,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = androidx.compose.ui.text.input.KeyboardType.Text),
                    trailingIcon = {
                        if (landmarkError) Icon(Icons.Filled.Error, contentDescription = "Error")
                    }
                )
                if (landmarkError) Text("Landmark is required", color = Color.Red, fontSize = 12.sp)
                //if (pincodeError) Text("Pincode is required", color = Color.Red, fontSize = 12.sp)
            }
            item {
                OutlinedTextField(
                    value = state,
                    onValueChange = {
                        state = it
                        stateError = it.isBlank()
                    },
                    label = { Text("State*") },
                    isError = stateError,
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        if (stateError) Icon(Icons.Filled.Error, contentDescription = "Error")
                    }
                )
                if (stateError) Text("State is required", color = Color.Red, fontSize = 12.sp)
            }
            item {
                OutlinedTextField(
                    value = city,
                    onValueChange = {
                        city = it
                        cityError = it.isBlank()
                    },
                    label = { Text("City*") },
                    isError = cityError,
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        if (cityError) Icon(Icons.Filled.Error, contentDescription = "Error")
                    }
                )
                if (cityError) Text("City is required", color = Color.Red, fontSize = 12.sp)
            }
            item {
                OutlinedTextField(
                    value = pincode,
                    onValueChange = {
                        pincode = it
                        pincodeError = it.isBlank()
                    },
                    label = { Text("Pincode*") },
                    isError = pincodeError,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number),
                    trailingIcon = {
                        if (pincodeError) Icon(Icons.Filled.Error, contentDescription = "Error")
                    }
                )
                if (pincodeError) Text("Pincode is required", color = Color.Red, fontSize = 12.sp)
            }

            item {
                val context=LocalContext.current
                Button(
                    onClick = {
                        fullNameError = fullName.isBlank()
//                        lastNameError = lastName.isBlank()
                        addressError = address.isBlank()
                        pincodeError = pincode.isBlank()
                        stateError = state.isBlank()
                        cityError = city.isBlank()

                        if (!fullNameError && !lastNameError && !addressError && !pincodeError && !stateError && !cityError) {
                            superGrocerViewModel./*updateAddress*/addAddressToDatabase(
                                "${fullName.trim()}\n${phoneno.toString().trim()}\n${address.trim()}, ${landmark.trim()}, ${state.trim()}, ${city.trim()} - ${pincode.trim()}"
                            )
                            superGrocerViewModel./*updateAddress*/addAddress(
                                "${fullName.trim()}\n${phoneno.toString().trim()}\n${address.trim()}, ${landmark.trim()}, ${state.trim()}, ${city.trim()} - ${pincode.trim()}"
                            )
                            fullName = ""
                            phoneno=""
                            address = ""
                            landmark = ""
                            pincode=""
                        }
                        Toast.makeText(context, "Address Saved", Toast.LENGTH_SHORT).show()
                        addAddress.value=!addAddress.value
                    },
                    enabled = fullName.isNotBlank() && address.isNotBlank() && pincode.isNotBlank() &&
                            state.isNotBlank() && city.isNotBlank(),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save Address")
                }
            }
        }
        }
    }