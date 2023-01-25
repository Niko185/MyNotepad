package com.example.mynotepad.fragments_operation

import androidx.fragment.app.Fragment


abstract class OptionalFragments : Fragment() {


    // Реализация прописана в конкретных Фрагментах(У каждого фрагмента своя реализация)
    // А запуск функции происходит в MainActivity
    abstract fun onClickAdd()
}
