package com.strathmore.electra

import android.provider.CalendarContract.Colors
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.strathmore.electra.ui.theme.AlegreyaFontFamily
import com.strathmore.electra.ui.theme.AlegreyaSansFontFamily

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    onGetStartedClick: () -> Unit = {},
    onSignInClick: () -> Unit = {}
){
    Box(
        modifier = modifier.fillMaxSize()
    ){
        Image(painter = painterResource(id = R.drawable.bg),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Image(painter = painterResource(id = R.drawable.logo1),
                contentDescription = null,
                modifier = Modifier.width(320.dp).height(240.dp),
                contentScale = ContentScale.Fit
                )

                Text("ELECTRA",
                    fontSize = 45.sp,
                    fontFamily = AlegreyaFontFamily,
                    fontWeight = FontWeight(700),
                    color = Color.White
                    )
            Text(
                "Authenticity in Democracy",
                textAlign = TextAlign.Center,
                fontFamily = AlegreyaSansFontFamily,
                fontSize = 25.sp,
                fontWeight = FontWeight(700),
                color = Color.White
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(onClick = onGetStartedClick,
                shape = MaterialTheme.shapes.large,
                colors  = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFE7C9A92)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                ){
                Text(text = "Get Started",
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontFamily = AlegreyaFontFamily,
                        fontWeight = FontWeight(500),
                        color = Color.White
                    )
                )
            }

            Row(
                modifier = Modifier.padding(top=12.dp, bottom = 52.dp)
            ){
                Text("Have An Account?",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = AlegreyaFontFamily,
                        color = Color.White
                    ))
                Text("Sign In",
                    modifier = Modifier.clickable{onSignInClick()},
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = AlegreyaFontFamily,
                        fontWeight = FontWeight(800),
                        color = Color.White
                    ))

            }

        }
    }

}

@Preview(showBackground = true, widthDp = 320 , heightDp = 640 )
@Composable
fun WelcomeScreenPrev(){
    WelcomeScreen()
}