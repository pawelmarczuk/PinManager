package com.example.passwordmanager.ui.password.newElement

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.passwordmanager.R
import com.example.passwordmanager.ui.theme.PasswordManagerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewPasswordScreen(
    modifier: Modifier = Modifier,
    viewModel: NewPasswordViewModel = viewModel(),
    onExit: () -> Unit = {},
    navController: NavHostController = rememberNavController()
) {
    val uiState by viewModel.uiState.collectAsState()
    val canNavigateBack = navController.previousBackStackEntry != null

    if (uiState.closeScreen) {
        onExit()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.titleAddPassword)) },
                navigationIcon = {
                    if (canNavigateBack) {
                        IconButton(onClick = {
                            navController.navigateUp()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.backButton)
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(15.dp)
        ) {
            OutlinedTextField(
                value = uiState.name,
                singleLine = true,
                shape = shapes.large,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { viewModel.updateName(it) },
                label = { Text(stringResource(R.string.labelPasswordName)) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
            )
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                OutlinedTextField(
                    value = uiState.password,
                    singleLine = true,
                    shape = shapes.large,
                    modifier = Modifier.weight(1f),
                    readOnly = true,
                    onValueChange = { },
                    label = { Text(stringResource(R.string.labelPassword)) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done
                    ),
                )
                IconButton(onClick = { viewModel.generatePassword() }) {
                    Icon(
                        Icons.Filled.Refresh,
                        contentDescription = stringResource(R.string.refresh)
                    )
                }
            }
            TextButton(
                enabled = uiState.buttonActive,
                onClick = { viewModel.save() },
            ) {
                Text(text = stringResource(R.string.save))
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun PasswordsListScreenPreview() {
    PasswordManagerTheme {
        NewPasswordScreen()
    }
}
