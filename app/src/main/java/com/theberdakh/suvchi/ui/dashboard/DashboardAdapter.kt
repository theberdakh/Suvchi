package com.theberdakh.suvchi.ui.dashboard

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.github.mikephil.charting.components.AxisBase
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.horizontal.HorizontalAxis
import com.patrykandpatrick.vico.core.chart.Chart
import com.patrykandpatrick.vico.core.chart.dimensions.HorizontalDimensions
import com.patrykandpatrick.vico.core.chart.insets.HorizontalInsets
import com.patrykandpatrick.vico.core.chart.insets.Insets
import com.patrykandpatrick.vico.core.chart.values.ChartValues
import com.patrykandpatrick.vico.core.component.marker.MarkerComponent
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.Shape
import com.patrykandpatrick.vico.core.component.shape.ShapeComponent
import com.patrykandpatrick.vico.core.component.text.TextComponent
import com.patrykandpatrick.vico.core.context.MeasureContext
import com.patrykandpatrick.vico.core.dimensions.Dimensions
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.ChartModelProducer
import com.patrykandpatrick.vico.core.entry.entriesOf
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.patrykandpatrick.vico.core.formatter.ValueFormatter
import com.patrykandpatrick.vico.core.marker.Marker
import com.patrykandpatrick.vico.core.marker.MarkerVisibilityChangeListener
import com.theberdakh.suvchi.data.local.demo.Analytics
import com.theberdakh.suvchi.databinding.ItemListDayChartBinding
import kotlinx.coroutines.Dispatchers

class DashboardAdapter(private val onDateClick: (View) -> Unit) :
    ListAdapter<Analytics, DashboardAdapter.DashboardViewHolder>(
        Analytics.Callback
    ) {
    inner class DashboardViewHolder(private val binding: ItemListDayChartBinding) :
        ViewHolder(binding.root) {

        fun bind(analytics: Analytics) {


            object: MarkerVisibilityChangeListener{
                override fun onMarkerHidden(marker: Marker)
                {
                    super.onMarkerHidden(marker)
                }

                override fun onMarkerMoved(
                    marker: Marker,
                    markerEntryModels: List<Marker.EntryModel>
                ) {
                    super.onMarkerMoved(marker, markerEntryModels)
                }

                override fun onMarkerShown(
                    marker: Marker,
                    markerEntryModels: List<Marker.EntryModel>
                ) {
                    super.onMarkerShown(marker, markerEntryModels)
                }

            }

            object: Marker {
                override fun getHorizontalInsets(
                    context: MeasureContext,
                    availableHeight: Float,
                    outInsets: HorizontalInsets
                ) {
                    super.getHorizontalInsets(context, availableHeight  + 20, outInsets)
                }

                override fun getInsets(
                    context: MeasureContext,
                    outInsets: Insets,
                    horizontalDimensions: HorizontalDimensions
                ) {
                    super.getInsets(context, outInsets, horizontalDimensions)
                }
            }
            // entriesOf(12f, 16f, 4f, 12f)
            val twoLine = entryModelOf(entriesOf(1 to 4f, 2 to 1, 3 to 5, 4 to 10, 5 to 12, 6 to 23, 7 to 13, 8 to 33, 9 to 11, 10 to 23, 11 to 18, 12 to 21))
            binding.title.text = analytics.title
            binding.chartView.setModel(twoLine)

          /*  class Formatter: ValueFormatter {
                val monthOfYear = listOf("Yanvar", "Fevral", "Mart", "Aprel", "May")
                override fun formatValue(value: Float, chartValues: ChartValues): CharSequence {
                   when(value.toInt()) {
                       1 -> "Yanvar"
                       else -> "Mart"
                   }
                }

            }
            val bottomAxisFormatter =
            (binding.chartView.bottomAxis as HorizontalAxis<AxisPosition.Horizontal.Bottom>).valueFormatter*/





        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        return DashboardViewHolder(
            ItemListDayChartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) =
        holder.bind(getItem(position))
}