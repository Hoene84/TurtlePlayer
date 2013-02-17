/**
 *
 * TURTLE PLAYER
 *
 * Licensed under MIT & GPL
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * More Information @ www.turtle-player.co.uk
 *
 * @author Simon Honegger (Hoene84)
 */

package turtle.player.util;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import turtle.player.R;
import turtle.player.model.Instance;
import turtle.player.presentation.InstanceFormatter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Should be created outside the UI Thread, the initial sorting in the constructor can take a long time
 * for big lists.
 */
public abstract class DefaultAdapter<T extends Instance> extends ArrayAdapter<T>
{
	private final List<T> objects;
	private final Activity activity;
	private final boolean allowsDuplicates;
	private final InstanceFormatter formatter;

	/**
	 * @param context needed by super
	 * @param objects has to be prepared according allowsDuplicates and has to be sorted (if needed)
	 * @param startingActivity to join UI Thread
	 * @param allowsDuplicates remove duplicates by adding items
	 * @param formatter
	 */
	public DefaultAdapter(
			  Context context,
			  List<T> objects,
			  Activity startingActivity,
			  boolean allowsDuplicates,
			  InstanceFormatter formatter)
	{
		super(context, R.layout.chooser_list_entry, objects);
		this.activity = startingActivity;
		this.objects = objects;
		this.allowsDuplicates = allowsDuplicates;
		this.formatter = formatter;
	}

	public void add(final T object)
	{
		if(allowsDuplicates || !objects.contains(object)){
			activity.runOnUiThread(new Runnable()
			{
				public void run()
				{
					objects.add(object);
					Collections.sort(objects, new FormattedInstanceComparator(formatter));
					notifyDataSetChanged();
				}
			});
		}
	}

	/**
	 * @param object objects has to be prepared according allowsDuplicates and has to be sorted (if needed)
	 */
	public void replace(final List<? extends T> object)
	{
		activity.runOnUiThread(new Runnable()
		{
			public void run()
			{
				objects.clear();
				objects.addAll(object);
				notifyDataSetChanged();
			}
		});
	}

	public void clear()
	{
		activity.runOnUiThread(new Runnable()
		{
			public void run()
			{
				objects.clear();
				notifyDataSetChanged();
			}
		});
	}

@Override
	public View getView(int position,
							  View convertView,
							  ViewGroup parent)
	{

		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.chooser_list_entry, parent, false);

		T currObject = getItem(position);

		TextView textView = (TextView) rowView.findViewById(R.id.label);
		textView.setText(currObject.accept(formatter));

		ImageView icon = (ImageView) rowView.findViewById(R.id.icon);
		if (isFilter(currObject))
		{
			icon.setImageResource(R.drawable.filter48_isfilter);
		}
		else if(isIncluder(currObject))
		{
			icon.setImageResource(R.drawable.filter48_include);
		}
		else if(isFiltered(currObject))
		{
			icon.setImageResource(R.drawable.filter48_filtered);
		}
		else
		{
			icon.setImageResource(R.drawable.filter48_notfiltered);
		}

		return rowView;
	}

	public abstract boolean isFiltered(T instance);

	public abstract boolean isFilter(T instance);

	public abstract boolean isIncluder(T instance);

	public abstract boolean filter(T instance);
}
