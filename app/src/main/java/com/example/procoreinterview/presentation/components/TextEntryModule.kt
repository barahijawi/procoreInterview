package com.example.procoreinterview.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.RemoveRedEye


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.procoreinterview.ui.theme.orange
import com.example.procoreinterview.ui.theme.white

@Composable
fun TextEntryModule(
    description: String,
    hint: String,
    leadingIcon: ImageVector,
    textValue: String,
    keyboardType: KeyboardType = KeyboardType.Ascii,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    textColor: Color,
    cursorColor: Color,
    onValueChanged: (String) -> Unit,
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: (() -> Unit)?,
    modifier: Modifier = Modifier

) {
    Column(modifier = modifier) {
        Text(
            text = description,
            color = textColor,
            style = MaterialTheme.typography.body2,

            )
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 3.dp)
                .border(0.5.dp, textColor, RoundedCornerShape((25.dp)))
                .height(50.dp)
                .shadow(3.dp, RoundedCornerShape(25.dp)),
            colors = TextFieldDefaults.textFieldColors(

                backgroundColor = white,
                cursorColor = cursorColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedLabelColor = Color.Transparent,
            ),
            value = textValue, onValueChange = onValueChanged,
            shape = RoundedCornerShape(25.dp),
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = leadingIcon, contentDescription = null,
                    tint = cursorColor
                )
            },
            trailingIcon = {
                if (trailingIcon != null) {
                    Icon(imageVector = trailingIcon, contentDescription = null,
                        tint = cursorColor,
                        modifier = Modifier.clickable {
                            if (onTrailingIconClick != null) {
                                onTrailingIconClick()
                            }
                        }
                    )
                }
            },
            placeholder = {
                Text(text = hint, style = MaterialTheme.typography.body2)

            },
            textStyle = MaterialTheme.typography.body2,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            visualTransformation = visualTransformation
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TextEntryModulePreview() {
    TextEntryModule(
        modifier = Modifier.fillMaxWidth()
            .padding(10.dp,0.dp,10.dp,5.dp),
        description = "Email address",
        hint = "email@domain.com",
        leadingIcon = Icons.Default.Email,
        textValue = "",
        textColor = Color.Black,
        cursorColor = orange,
        onValueChanged = { println("onvalueChangedText") },
        onTrailingIconClick = { println("onTrailingIconClick")

        },
        trailingIcon = Icons.Default.RemoveRedEye,
visualTransformation = PasswordVisualTransformation()
    )
}