package com.example.passwordmanager.domain.converter

import com.example.passwordmanager.domain.data.Password

fun com.example.passwordmanager.datalayer.db.model.PasswordEntity.toDomain() = Password(
    id = id,
    name = name,
    password = password,
)