package com.example.passwordmanager.domain.usecase

class GeneratePasswordUseCase() {
    operator fun invoke(): String {
        val charset = ('0'..'9')
        return (1..6)
            .map { charset.random() }
            .joinToString("")
    }
}
