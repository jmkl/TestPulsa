package arm.testpulsa.widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import arm.testpulsa.R;

//FIXME something must be wrong here
public class PulsaWidget extends AppWidgetProvider{

	public void onReceive(Context context, Intent intent)
	{
		String action = intent.getAction();
		if (AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(action))
		{
			
			RemoteViews views = new RemoteViews(context.getPackageName(),
					R.layout.activity_main);

			AppWidgetManager
					.getInstance(context)
					.updateAppWidget(
							intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS),
							views);
		}
	}
}