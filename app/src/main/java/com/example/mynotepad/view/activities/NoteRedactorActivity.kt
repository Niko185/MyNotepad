package com.example.mynotepad.activities
import android.annotation.SuppressLint
import  android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import com.example.mynotepad.R
import com.example.mynotepad.databinding.ActivityNoteRedactorBinding
import com.example.mynotepad.entities.NoteItemData
import com.example.mynotepad.fragments.NoteFragment
import com.example.mynotepad.utils.HtmlManager
import com.example.mynotepad.utils.MotionColorPicker
import com.example.mynotepad.utils.TimeManager


class NoteRedactorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNoteRedactorBinding
    private var noteItemData: NoteItemData? = null
    lateinit var pref: SharedPreferences

        // Activity Functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteRedactorBinding.inflate(layoutInflater)
            setContentView(binding.root)
            actionBarSettings()
            getAllNoteItemData()
            initObjects()
            setTextSizeDone()
            setTextSizeDone()
            onClickColorPicker()
    }


        // ActionBar Functions
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.note_redactor_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(menuRedactorItem: MenuItem): Boolean {
        if(menuRedactorItem.itemId == R.id.IconSave) {
                launcherIdentificationSendBackAnswerIntent()
        } else if (menuRedactorItem.itemId == android.R.id.home) {
            finish()
        } else if (menuRedactorItem.itemId == R.id.IconBold) {
            setBoldText()
        } else if (menuRedactorItem.itemId == R.id.IconColorPicker) {
            if(binding.colorPicker.isShown) {
                closeColorPicker()
            } else openColorPicker()

        }
        return super.onOptionsItemSelected(menuRedactorItem)
    }

    private fun actionBarSettings(){
        var actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }


    // Launcher Functions NoteRedactorActivity <-> NoteFragment
    private fun launcherIdentificationSendBackAnswerIntent() {
        var stateIdentification = "key_new_identification"
        val newIdentification: NoteItemData? = if(noteItemData == null) {
            createNewItemNote()
        } else {
            stateIdentification = "key_edit_identification"
            updateItemNote()
        }
        val intent = Intent().apply {
            putExtra(NoteFragment.KEY_FOR_CREATE_NEW_NOTE_ITEM, newIdentification)
            putExtra(NoteFragment.KEY_FOR_EDIT_NOTE_ITEM, stateIdentification )
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun getAllNoteItemData(){
        val serializableNote = intent.getSerializableExtra(NoteFragment.KEY_FOR_CREATE_NEW_NOTE_ITEM)
        if(serializableNote != null) {
            noteItemData = serializableNote as NoteItemData
            fillItemNote()
        }
    }

    private fun fillItemNote() = with(binding){
        nameNoteRedactor.setText(noteItemData?.columnName)
        descriptionNoteRedactor.setText(HtmlManager.getTextFromHtml(noteItemData?.columnDescription!!).trim())
    }

    private fun createNewItemNote(): NoteItemData {
        return NoteItemData(
            null,
            binding.nameNoteRedactor.text.toString(),
            HtmlManager.toHtml(binding.descriptionNoteRedactor.text),
            TimeManager.getCurrentTime(),
            ""
        )
    }

    private fun updateItemNote() : NoteItemData? = with(binding) {
        return noteItemData?.copy(
            columnName = nameNoteRedactor.text.toString(),
            columnDescription = HtmlManager.toHtml(descriptionNoteRedactor.text)
        )
    }


    // Bold Text style functions
    private fun setBoldText() = with(binding) {
        val startPosition = descriptionNoteRedactor.selectionStart
        val endPosition = descriptionNoteRedactor.selectionEnd

        val styles = descriptionNoteRedactor.text.getSpans(startPosition, endPosition, StyleSpan::class.java)
        var boldStyle: StyleSpan? = null
        if(styles.isNotEmpty()){
            descriptionNoteRedactor.text.removeSpan(styles[0])
        } else {
            boldStyle = StyleSpan(Typeface.BOLD)
        }
        descriptionNoteRedactor.text.setSpan(boldStyle, startPosition, endPosition, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        descriptionNoteRedactor.text.trim()
        descriptionNoteRedactor.setSelection(startPosition)
    }


     // Color Picker Functions
    private fun openColorPicker() {
        binding.colorPicker.visibility = View.VISIBLE
        val beginAnimation = AnimationUtils.loadAnimation(this, R.anim.open_picker)
        binding.colorPicker.startAnimation(beginAnimation)
    }

    private fun closeColorPicker() {
        val endAnimation = AnimationUtils.loadAnimation(this, R.anim.close_picker)
        endAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
            }
            override fun onAnimationEnd(p0: Animation?) {
                binding.colorPicker.visibility = View.GONE
            }
            override fun onAnimationRepeat(p0: Animation?) {
            }
        })
        binding.colorPicker.startAnimation(endAnimation)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initObjects() {
        binding.colorPicker.setOnTouchListener(MotionColorPicker())
        pref = PreferenceManager.getDefaultSharedPreferences(this)
    }

    private fun setColorForText(colorID: Int) = with(binding) {
        val startPosition = descriptionNoteRedactor.selectionStart
        val endPosition = descriptionNoteRedactor.selectionEnd


        val styles = descriptionNoteRedactor.text.getSpans(startPosition, endPosition, ForegroundColorSpan::class.java)
        if(styles.isNotEmpty()) { descriptionNoteRedactor.text.removeSpan(styles[0]) }


        descriptionNoteRedactor.text.setSpan(ForegroundColorSpan(ContextCompat.getColor(this@NoteRedactorActivity, colorID)), startPosition, endPosition, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        descriptionNoteRedactor.text.trim()
        descriptionNoteRedactor.setSelection(startPosition)
    }

    private fun onClickColorPicker() = with(binding){
        buttonPicBlue.setOnClickListener {
            setColorForText(R.color.color_picker_blue)
        }

        buttonPicGreen.setOnClickListener {
            setColorForText(R.color.color_picker_green)
        }

        buttonPicYellow.setOnClickListener {
            setColorForText(R.color.color_picker_yellow)
        }

        buttonPicBlack.setOnClickListener {
            setColorForText(R.color.color_picker_black)
        }

        buttonPicGray.setOnClickListener {
            setColorForText(R.color.color_picker_grey)
        }

        buttonPicOrange.setOnClickListener {
            setColorForText(R.color.color_picker_orange)
        }
    }

    //Settings Preference Functions


    private fun EditText.setTextSize(size: String?) {
        if(size != null) this.textSize = size.toFloat()
    }

    private fun setTextSizeDone() = with(binding) {
        nameNoteRedactor.setTextSize(pref.getString("key_text_size_title", "20"))
        descriptionNoteRedactor.setTextSize(pref.getString("key_text_size_description", "16"))
    }


}