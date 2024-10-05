import com.example.heptotech.bean_dataclass.DateModel
import java.util.*

object DateUtil {

    // Function to check if the year is a leap year
    fun isLeapYear(year: Int): Boolean {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
    }

    // Function to get the number of days in a given month and year
    fun getDaysInMonth(month: Int, year: Int): Int {
        return when (month) {
            Calendar.JANUARY, Calendar.MARCH, Calendar.MAY, Calendar.JULY, Calendar.AUGUST, Calendar.OCTOBER, Calendar.DECEMBER -> 31
            Calendar.APRIL, Calendar.JUNE, Calendar.SEPTEMBER, Calendar.NOVEMBER -> 30
            Calendar.FEBRUARY -> if (isLeapYear(year)) 29 else 28
            else -> 0
        }
    }

    // Function to generate the list of dates for a given month and year
    fun getDatesForMonth(month: Int, year: Int): List<DateModel> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.YEAR, year)

        val daysInMonth = getDaysInMonth(month, year)
        val dateList = mutableListOf<DateModel>()

        for (i in 1..daysInMonth) {
            calendar.set(Calendar.DAY_OF_MONTH, i)
            val dayOfWeek = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault()) ?: ""
            val monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) ?: ""

            dateList.add(DateModel(dayOfWeek, i, monthName, year))
        }

        return dateList
    }

    // Function to move to the next month
    fun getNextMonth(month: Int, year: Int): Pair<Int, Int> {
        return if (month == Calendar.DECEMBER) {
            Pair(Calendar.JANUARY, year + 1) // Move to January of the next year
        } else {
            Pair(month + 1, year) // Move to the next month in the same year
        }
    }

    // Function to move to the previous month
    fun getPreviousMonth(month: Int, year: Int): Pair<Int, Int> {
        return if (month == Calendar.JANUARY) {
            Pair(Calendar.DECEMBER, year - 1) // Move to December of the previous year
        } else {
            Pair(month - 1, year) // Move to the previous month in the same year
        }
    }
}
