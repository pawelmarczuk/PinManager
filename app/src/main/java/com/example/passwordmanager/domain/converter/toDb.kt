package com.example.passwordmanager.domain.converter

import com.example.passwordmanager.domain.data.Password


fun Password.toDb() = com.example.passwordmanager.datalayer.db.model.PasswordEntity(
    id = id,
    name = name,
    password = password,
)