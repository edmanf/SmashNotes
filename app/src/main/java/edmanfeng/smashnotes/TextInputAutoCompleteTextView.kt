/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edmanfeng.smashnotes

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewParent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.widget.AutoCompleteTextView
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.appcompat.widget.AppCompatEditText
import com.google.android.material.R
import com.google.android.material.textfield.TextInputLayout

/**
 * A special sub-class of [android.widget.EditText] designed for use as a child of [ ].
 *
 *
 * Using this class allows us to display a hint in the IME when in 'extract' mode and provides
 * accessibility support for [com.google.android.material.textfield.TextInputLayout].
 */
class TextInputAutoCompleteTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.editTextStyle
) : AutoCompleteTextView(context, attrs, defStyleAttr) {

    private val textInputLayout: TextInputLayout?
        get() {
            var parent = parent
            while (parent is View) {
                if (parent is TextInputLayout) {
                    return parent
                }
                parent = parent.getParent()
            }
            return null
        }

    private val hintFromLayout: CharSequence?
        get() {
            val layout = textInputLayout
            return layout?.hint
        }

    override fun onCreateInputConnection(outAttrs: EditorInfo): InputConnection? {
        val ic = super.onCreateInputConnection(outAttrs)
        if (ic != null && outAttrs.hintText == null) {
            // If we don't have a hint and our parent is a TextInputLayout, use its hint for the
            // EditorInfo. This allows us to display a hint in 'extract mode'.
            outAttrs.hintText = hintFromLayout
        }
        return ic
    }
}