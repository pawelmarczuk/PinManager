package com.example.passwordmanager.ui.password.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.passwordmanager.R
import com.example.passwordmanager.domain.data.Password
import com.example.passwordmanager.ui.theme.PasswordManagerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordsListScreen(
    modifier: Modifier = Modifier,
    viewModel: PasswordsListViewModel = viewModel(),
    onAddNewItemClicked: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()
    var menuExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(true) {
        viewModel.loadData()
    }

    uiState.idItemToDelete?.let {
        RemovePasswordAlertDialog(password = it, viewModel = viewModel)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                actions = {
                    if (uiState.deleteMode) {

                        ActionBarInDeleteMode(
                            onTurnOffDeleteOption = { viewModel.turnOffDeleteOption() }
                        )
                    } else {
                        ActionBar(
                            onAddNewItemClicked = onAddNewItemClicked,
                            onMenuClicked = { menuExpanded = !menuExpanded },
                            menuExpanded = menuExpanded,
                            onTurnOnDeleteOption = {
                                menuExpanded = false
                                viewModel.turnOnDeleteOption()
                            }
                        )
                    }
                }
            )
        },


        ) { innerPadding ->
        ShowList(
            data = uiState.list,
            viewModel = viewModel,
            modifier = modifier.padding(innerPadding),
            deleteOption = uiState.deleteMode
        )
    }
}

@Composable
fun ActionBar(
    onAddNewItemClicked: () -> Unit,
    menuExpanded: Boolean,
    onMenuClicked: () -> Unit,
    onTurnOnDeleteOption: () -> Unit,
) {
    IconButton(onClick = { onAddNewItemClicked.invoke() }) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(R.string.add),
        )
    }
    IconButton(onClick = { onMenuClicked() }) {
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = stringResource(R.string.more),
        )
    }
    DropdownMenu(
        expanded = menuExpanded,
        onDismissRequest = { onMenuClicked() },
    ) {

        DropdownMenuItem(
            text = {
                Text(stringResource(R.string.delete))
            },
            onClick = { onTurnOnDeleteOption() },
        )
    }
}

@Composable
fun ActionBarInDeleteMode(
    onTurnOffDeleteOption: () -> Unit,
) {
    IconButton(onClick = { onTurnOffDeleteOption() }) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = stringResource(R.string.close),
        )
    }
}

@Composable
fun ShowList(
    viewModel: PasswordsListViewModel,
    data: List<Password>,
    deleteOption: Boolean,
    modifier: Modifier = Modifier
) {
    if (data.isEmpty()) {
        Row(
            modifier = modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(Icons.Filled.Warning, stringResource(R.string.warning), Modifier.padding(10.dp))
            Text(stringResource(R.string.noData), Modifier.padding(10.dp))
        }
    } else {
        LazyColumn(modifier.fillMaxSize()) {
            items(data) {
                ListItem(deleteOption = deleteOption, viewModel = viewModel, data = it)
            }
        }
    }
}

@Composable
fun ListItem(
    viewModel: PasswordsListViewModel,
    data: Password,
    deleteOption: Boolean,
    modifier: Modifier = Modifier
) {
    Row(modifier.fillMaxWidth()) {

        Text(
            text = data.name, modifier = Modifier
                .weight(1f)
                .padding(10.dp)
        )
        Text(
            text = data.password, modifier = Modifier
                .weight(1f)
                .padding(10.dp)
        )
        if (deleteOption) {
            IconButton(onClick = { viewModel.showDialogForRemovePassword(data) }) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = stringResource(R.string.delete),
                )
            }
        }
    }
}


@Composable
fun RemovePasswordAlertDialog(
    password: Password,
    viewModel: PasswordsListViewModel,
) {
    AlertDialog(
        onDismissRequest = {
            viewModel.skipRemovePassword()
        },
        title = { Text(stringResource(R.string.removePasswordDialogTitle)) },
        text = {
            Text(
                stringResource(
                    R.string.removePasswordDialogContent,
                    password.name,
                    password.password
                )
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    viewModel.removePassword(password)
                }
            ) {
                Text(
                    stringResource(R.string.confirm),
                    color = Color.White
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PasswordsListScreenPreview() {
    PasswordManagerTheme {
        PasswordsListScreen()
    }
}