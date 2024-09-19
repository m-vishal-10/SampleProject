package com.icebrekr.icebrekrui.moduleviews.leadgeneration.businesscardreader.review.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.icebrekr.icebrekrui.configurables.color.IBTheme

//region Progress Steps View
@Composable
fun ProgressStepsView(currentStep: Int, totalSteps: Int) {

    val fillColor =
        IBTheme.theme.primary2Default // Assuming IBTheme.theme.primary2Default is a Color resource

    Row(
        horizontalArrangement = Arrangement.spacedBy(0.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (step in 0 until totalSteps) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .background(
                        color = if (step <= currentStep) fillColor else Color.Transparent,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                // Circle stroke
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(
                            color = Color.Transparent,
                            shape = CircleShape
                        )
                        .border(width = 2.dp, color = fillColor, shape = CircleShape)
                )
            }

            // Divider between steps (except for the last one)
            if (step < totalSteps - 1) {
                Box(
                    modifier = Modifier
                        .height(2.dp)
                        .width(24.dp)
                        .background(color = fillColor)
                )
            }
        }
    }
}
//endregion

//region Preview
@Preview
@Composable
fun ProgressStepsViewPreview() {
    Column {
        Spacer(modifier = Modifier.height(8.dp))
        ProgressStepsView(currentStep = 0, totalSteps = 6)
        Spacer(modifier = Modifier.height(8.dp))
        ProgressStepsView(currentStep = 1, totalSteps = 6)
        Spacer(modifier = Modifier.height(8.dp))
        ProgressStepsView(currentStep = 2, totalSteps = 6)
        Spacer(modifier = Modifier.height(8.dp))
        ProgressStepsView(currentStep = 3, totalSteps = 6)
        Spacer(modifier = Modifier.height(8.dp))
        ProgressStepsView(currentStep = 4, totalSteps = 6)
        Spacer(modifier = Modifier.height(8.dp))
        ProgressStepsView(currentStep = 5, totalSteps = 6)
        Spacer(modifier = Modifier.height(8.dp))
    }
}
//endregion