/*
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
 * Created by Edd Turtle (www.eddturtle.co.uk)
 * More Information @ www.turtle-player.co.uk
 *
 */

package com.turtleplayer.playlist.playorder;

import android.util.Log;
import ch.hoene.perzist.access.executor.OperationExecutor;
import ch.hoene.perzist.access.paging.Paging;
import ch.hoene.perzist.access.sort.OrderSet;
import ch.hoene.perzist.access.sort.SortOrder;
import ch.hoene.perzist.android.FirstSqlLite;
import ch.hoene.perzist.android.QuerySqlite;
import ch.hoene.perzist.android.ReadOperationSqlLite;
import com.turtleplayer.model.Track;
import com.turtleplayer.persistance.turtle.db.TurtleDatabase;
import com.turtleplayer.persistance.turtle.db.structure.Tables;
import com.turtleplayer.persistance.turtle.mapping.TrackCreator;
import com.turtleplayer.playlist.Playlist;

public class PlayOrderSorted implements PlayOrderStrategy
{

	private final Playlist playlist;
	private final TurtleDatabase db;

	public PlayOrderSorted(final TurtleDatabase db,
								  final Playlist playlist)
	{
		this.playlist = playlist;
		this.db = db;
	}

	public Track getNext(Track currTrack)
	{
		return get(currTrack, new DefaultOrder(SortOrder.ASC));
	}

	public Track getPrevious(Track currTrack)
	{
		return get(currTrack, new DefaultOrder(SortOrder.DESC));
	}

	private Track get(Track ofTrack, OrderSet<? super Tables.Tracks> order)
	{
		OrderSet<? super Tables.Tracks> currOrder = order;
		while(!currOrder.isEmpty())
		{
			Log.v(PlayOrderSorted.class.getName(),
				"Generate Paging Filters from: " + order);
			Log.v(PlayOrderSorted.class.getName(),
				"resulting in Paging Filters : " + Paging.getFilter(playlist.getCompressedFilter(), ofTrack, currOrder));
			Track nextTrack = OperationExecutor.execute(
				db,
				new ReadOperationSqlLite<Track>(
					new QuerySqlite<Tables.Tracks, Tables.Tracks, Track>(
						Paging.getFilter(playlist.getCompressedFilter(), ofTrack, currOrder),
						order,
						new FirstSqlLite<Track>(Tables.TRACKS, new TrackCreator())
					))
			);
			if(nextTrack != null){
				return nextTrack;
			}
			currOrder = currOrder.removeLast();
		}
		return null;
	}
}
