package com.princemaurya.rewayat000.ui.grow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.princemaurya.rewayat000.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LearnViewModel @Inject constructor(
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _courses = MutableLiveData<List<Course>>()
    val courses: LiveData<List<Course>> = _courses

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadCourses()
    }

    private fun loadCourses() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                firestore.collection("courses").addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        _courses.value = getMockCourses()
                        return@addSnapshotListener
                    }

                    val coursesList = snapshot?.documents?.mapNotNull { doc ->
                        Course(
                            title = doc.getString("title") ?: "",
                            description = doc.getString("description") ?: "",
                            duration = doc.getString("duration") ?: "",
                            thumbnailRes = doc.getLong("thumbnailRes")?.toInt() ?: R.mipmap.ic_launcher
                        )
                    } ?: getMockCourses()

                    _courses.value = coursesList
                }
            } catch (e: Exception) {
                _courses.value = getMockCourses()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun getMockCourses() = listOf(
        Course("Digital Marketing Basics", "Learn online selling strategies", "2h 30m", R.mipmap.ic_launcher),
        Course("Product Photography", "Master craft photography techniques", "1h 45m", R.mipmap.ic_launcher),
        Course("Business Management", "Essential business skills for artisans", "3h 15m", R.mipmap.ic_launcher)
    )
}
