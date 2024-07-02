package com.mostafadevo.todotrackercompose.ui

import androidx.lifecycle.ViewModel
import com.mostafadevo.todotrackercompose.domain.repository.ITodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val ITodoRepository: ITodoRepository
) : ViewModel() {}