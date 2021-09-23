package com.example.stateflowtest

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _state = MutableStateFlow(UIState())
    val state: Flow<UIState> = _state

    init {
        // This sets the articles in state. Just simulating getting values back from some service.
        viewModelScope.launch {
            val result = getArticles()
            result.fold(
                onSuccess = {
                    _state.value = _state.value.copy(
                        articles = it,
                    )
                },
                onFailure = { e ->
                    Log.e("Error", e.toString())
                }
            )
        }
    }

    fun handlePressed(article: Article, tag: Tag) {
        Log.i("Pressed", article.toString())
        Log.i("Pressed", tag.toString())

        // This toggles isSelected of every tag based on it's id
        article.tags.map {
            it.isSelected = it.id == tag.id
        }

        // Doing a deep copy of articles to try to cast it as a new list
        val updatedArticles = deepCopy(_state.value.articles)

        // Updating all of the articles with the correct values,
        // because updating an individual tag doesn't seem to be possible
        _state.value = _state.value.copy(
            articles = updatedArticles
        )
    }
}

fun getArticles(): Result<List<Article>> {
    return Result.success(article1)
}

data class UIState (
    val articles: List<Article> = emptyList(),
)

data class Article (
    val author: Author,
    val title: String = "",
    val tags: List<Tag>
)

data class Author (
    val first: String = "",
    val last: String = ""
)

data class Tag (
    val id: Int = 0,
    val label: String = "",
    var isSelected: Boolean = false
)

val article1 = listOf(
    Article (
        author = Author(
            first = "Test",
            last = "Name"
        ),
        title = "Article 0",
        tags = listOf(
            Tag(id = 1, label = "Fiction"),
            Tag(id = 3, label = "Some other tag")
        )
    ),
    Article (
        author = Author(
            first = "Test",
            last = "Name"
        ),
        title = "Article 1",
        tags = listOf(
            Tag(id = 2, label = "Science Fiction"),
            Tag(id = 3, label = "Some other tag")
        )
    )
)

private fun deepCopy(questions: List<Article>) : List<Article> {
    val type = object : TypeToken<List<Article>>() {}.type
    val serialize = Gson().toJson(questions)
    return Gson().fromJson(serialize, type)
}
