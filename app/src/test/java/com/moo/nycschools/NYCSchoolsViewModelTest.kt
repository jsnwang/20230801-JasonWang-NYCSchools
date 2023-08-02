import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.moo.nycschools.model.HighSchool
import com.moo.nycschools.model.NYCSchoolsRepository
import com.moo.nycschools.util.Status
import com.moo.nycschools.util.UiState
import com.moo.nycschools.viewmodel.NYCSchoolsViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class NYCSchoolsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repo: NYCSchoolsRepository

    private lateinit var viewModel: NYCSchoolsViewModel

    @Mock
    private lateinit var observer: Observer<UiState<List<HighSchool>>>

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = NYCSchoolsViewModel(repo)
    }

    @Test
    fun `getSchools success returns list of schools`() = runTest {
        // Given
        val mockData = listOf(HighSchool(), HighSchool())
        `when`(repo.getHighSchools()).thenReturn(mockData)

        // When
        viewModel.getSchools()
        viewModel.highSchoolsState.observeForever(observer)

        // Then
        val value = viewModel.highSchoolsState.value
        assertEquals(Status.SUCCESS, value?.status)
        assertEquals(mockData, value?.data)
    }

    @Test
    fun `getSchools returns error when exception is thrown`() = runTest {
        // Given
        val exception = RuntimeException("An error occurred")
        `when`(repo.getHighSchools()).thenThrow(exception)

        // When
        viewModel.getSchools()
        viewModel.highSchoolsState.observeForever(observer)

        // Then
        val value = viewModel.highSchoolsState.value
        assertEquals(Status.ERROR, value?.status)
        assertEquals(exception, value?.error)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
        viewModel.highSchoolsState.removeObserver(observer)
    }
}
