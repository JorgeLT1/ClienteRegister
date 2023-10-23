package com.example.registerwithapi.ui.theme.Cliente

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun PantallaCliente(viewModel: ClienteViewModel = hiltViewModel()) {

    val clientes by viewModel.clientes.collectAsState()

    var isSaveButtonClicked by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(Unit) {
        viewModel.isMessageShownFlow.collectLatest {
            if (it) {
                snackbarHostState.showSnackbar(
                    message = "Cliente guardado",
                    duration = SnackbarDuration.Short
                )
            }
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Registro cliente") },
                actions = {
                    IconButton(onClick = {
                        viewModel.nombres = ""
                        viewModel.rnc = ""
                        viewModel.direccion = ""
                        viewModel.limiteCredito = 0

                    }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh"
                        )

                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {

                OutlinedTextField(
                    value = viewModel.nombres,
                    onValueChange = { viewModel.nombres = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Nombre") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = viewModel.rnc,
                    onValueChange = { viewModel.rnc = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Rnc") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = viewModel.direccion,
                    onValueChange = { viewModel.direccion = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Dirección") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = viewModel.limiteCredito.toString(),
                    onValueChange = {
                        val newValue = it.toIntOrNull()
                        if (newValue != null) {
                            viewModel.limiteCredito = newValue
                        }

                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Límite de crédito") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )
                val keyboardController =
                    LocalSoftwareKeyboardController.current
                OutlinedButton(
                    onClick = {
                        keyboardController?.hide()
                        if (viewModel.validar()) {
                            viewModel.save()
                            viewModel.setMessageShown()
                        }
                    }
                )
                {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Guardar"
                    )
                    Text(text = "Guardar")
                }
            }


        }
    }
}



