package com.example.sosapplication

import androidx.lifecycle.*
import com.example.sosapplication.database.ContactssDao
import com.example.sosapplication.database.EmergencyContactTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactViewModel(private val contactssDao: ContactssDao):ViewModel() {
    val contactList:LiveData<List<EmergencyContactTable>> = contactssDao.getItems().asLiveData()

    fun editContact(contact: EmergencyContactTable){
        viewModelScope.launch(Dispatchers.IO) {
            contactssDao.update(contact)
        }
    }

    fun deleteContact(contact: EmergencyContactTable){
        viewModelScope.launch(Dispatchers.IO){
            contactssDao.delete(contact)
        }
    }
    fun addContact(contact: EmergencyContactTable){
        viewModelScope.launch(Dispatchers.IO) {
            contactssDao.insert(contact)
        }
    }

}
class ContactsViewModelFactory(private val contactssDao: ContactssDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ContactViewModel(contactssDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}