package pages.company

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.Page
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text

@Page(routeOverride = "/company")
@Composable
fun CompanyListPage() {
    H1 { Text("Companies") }
}
