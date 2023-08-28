package com.olgag.currencyconverter.screens

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.Intent.createChooser
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.olgag.currencyconverter.BuildConfig
import com.olgag.currencyconverter.R
import com.olgag.currencyconverter.components.DrawerScreens
import com.olgag.currencyconverter.components.TopBar
import com.olgag.currencyconverter.components.TxtButton

@Composable
fun About(navController: NavController) {
    val context: Context = LocalContext.current
    Column(modifier = Modifier
        .fillMaxSize()
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    MaterialTheme.colors.background,
                    MaterialTheme.colors.secondary
                )
            )
        )
    ) {
        TopBar(
            title = stringResource(id = DrawerScreens.About.title),
            buttonIcon = Icons.Filled.ArrowBack,
            onButtonClicked = { navController.popBackStack() }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start) {
            Text(
                text = "Introducing \"CurrenVerter\" - a user-friendly and versatile app that will be your perfect travel companion. With its simple interface and easy-to-use features, \"CurrenVerter\" makes currency conversion a breeze wherever you go.",
                style = MaterialTheme.typography.h3
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = "Key Features:",
                style = MaterialTheme.typography.h3
            )
            Spacer(Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.Top) {
                Icon(Icons.Rounded.CheckCircle, "")
                Text(
                    text = "Comprehensive Currency Rates: Access almost all currency rates from around the world, ensuring you stay updated with the latest exchange rates.",
                    style = MaterialTheme.typography.h3
                )
            }
            Spacer(Modifier.height(5.dp))
            Row(verticalAlignment = Alignment.Top) {
                Icon(Icons.Rounded.CheckCircle, "")
                ClickableLinkText(
                    "Real-time Data: Our app fetches currency rates from the reliable source - ",
                    "The Free Currency Converter API",
                    " - ensuring accuracy and reliability.",
                    "https://www.currencyconverterapi.com/",
                    context
                )
            }
            Spacer(Modifier.height(5.dp))
            Row(verticalAlignment = Alignment.Top) {
                Icon(Icons.Rounded.CheckCircle, "")
                Text(
                    text = "Offline Currency Conversion: No internet? No problem! \"CurrenVerter\" allows you to perform self-conversions even when you are offline, making it truly reliable on the go.",
                    style = MaterialTheme.typography.h3
                )
            }
            Spacer(Modifier.height(16.dp))
            ClickableLinkText(
                "Special Thanks: A heartfelt thanks to ",
                "Icons8",
                " for providing the beautifully crafted icons that perfectly complement our app's design.",
                "https://icons8.com",
                context
            )

            Row( horizontalArrangement = Arrangement.Center,
                 verticalAlignment = Alignment.CenterVertically,
                 modifier = Modifier
                     .fillMaxWidth()
                     .padding(50.dp)) {
                TxtButton(
                    stringResource(id = R.string.share),
                    R.drawable.share,
                    onButtonClicked = { shareContent(context) })
            }
        }
    }
}

@Composable
fun ClickableLinkText(
    startText: String,
    clickableWord: String,
    endText: String,
    url: String,
    context: Context
) {
    val annotatedString = buildAnnotatedString {
        append(startText)
        withStyle(style = SpanStyle(color = colorResource(R.color.link), textDecoration = TextDecoration.Underline)) {
            pushStringAnnotation(tag = clickableWord, annotation = clickableWord)
            append(clickableWord)
        }
        append(endText)
    }
    ClickableText(text = annotatedString,
        style = MaterialTheme.typography.h3,
        onClick = { offset ->
        annotatedString.getStringAnnotations(offset, offset)
            .firstOrNull()?.let { span ->
                openUrl(context, url)
            }
    })
}

private fun openUrl(context: Context, url: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        val chooser = Intent.createChooser(intent,null)
        context.startActivity(chooser)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
    }
}

private fun shareContent(context: Context) {
    try {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name)
        var shareMessage = "\nLet me recommend you this application\n\n"
        shareMessage =
            """
            ${shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID}
            """.trimIndent()
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
        context.startActivity(createChooser(shareIntent,"choose one"))
    } catch (e: Exception) {
        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
    }
}