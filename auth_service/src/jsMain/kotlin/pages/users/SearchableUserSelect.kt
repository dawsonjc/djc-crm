package org.example.pages.users

import androidx.compose.runtime.*
import org.jetbrains.compose.web.attributes.*
import org.jetbrains.compose.web.dom.*

/** Search narrows the native select without changing the saved selection. */
@Composable
internal fun SearchableUserSelect(
    id: String, label: String, value: String, options: List<Pair<String, String>>, onChange: (String) -> Unit,
) {
    var query by remember(id) { mutableStateOf("") }
    val choices = options.distinctBy { it.first }.let { catalog ->
        if (catalog.any { it.first == value }) catalog else catalog + (value to value)
    }
    val search = query.trim()
    val matches = choices.filter { (key, text) ->
        key.contains(search, ignoreCase = true) || text.contains(search, ignoreCase = true)
    }
    // Keep the selected value in the DOM even when it doesn't match the search.
    // Otherwise the browser may visually select the first match without updating the form state.
    val visible = if (matches.any { it.first == value }) matches else
        choices.filter { it.first == value } + matches

    Div(attrs = { classes("col-12", "col-md-6") }) {
        Label(forId = id, attrs = { classes("form-label") }) { Text(label) }
        Label(forId = "$id-search", attrs = { classes("visually-hidden") }) { Text("Search $label") }
        Input(InputType.Search, attrs = {
            id("$id-search")
            classes("form-control", "form-control-sm", "mb-2")
            value(query)
            attr("placeholder", "Search ${label.lowercase()}…")
            attr("aria-controls", id)
            attr("autocomplete", "off")
            onInput { query = it.value }
            // Searching inside a form should not submit the whole user draft.
            onKeyDown { if (it.key == "Enter") it.preventDefault() }
        })
        Select(attrs = {
            id(id)
            classes("form-select")
            attr("aria-describedby", "$id-results")
            onChange { event -> event.value?.let(onChange) }
        }) {
            visible.forEach { (key, text) ->
                key(key) {
                    Option(key, attrs = { if (key == value) selected() }) { Text(text) }
                }
            }
        }
        Div(attrs = { id("$id-results"); classes("form-text"); attr("role", "status") }) {
            Text(when {
                search.isEmpty() -> "${choices.size} options available."
                matches.isEmpty() -> "No matching options. Current selection retained."
                matches.none { it.first == value } -> "${matches.size} matching options, plus your current selection."
                else -> "${matches.size} matching options."
            })
        }
    }
}
internal fun userTimeZones(): List<String> {
    val supported = js("typeof Intl.supportedValuesOf === 'function' ? Intl.supportedValuesOf('timeZone') : []")
        .unsafeCast<Array<String>>()
    // Fallback choices for older browsers without Intl.supportedValuesOf.
    val zones = if (supported.isNotEmpty()) supported.toList() else listOf(
        "America/Anchorage", "America/Chicago", "America/Denver", "America/Los_Angeles",
        "America/New_York", "America/Phoenix", "America/Toronto", "America/Sao_Paulo",
        "Europe/Amsterdam", "Europe/Berlin", "Europe/London", "Europe/Paris",
        "Africa/Cairo", "Africa/Johannesburg", "Asia/Dubai", "Asia/Kolkata",
        "Asia/Shanghai", "Asia/Singapore", "Asia/Tokyo", "Australia/Sydney", "Pacific/Auckland", "Pacific/Honolulu",
    )
    return listOf("UTC") + zones.filter { it != "UTC" }.distinct().sorted()
}