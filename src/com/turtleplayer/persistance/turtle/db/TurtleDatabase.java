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

package com.turtleplayer.persistance.turtle.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import ch.hoene.perzist.access.creator.ResultCreator;
import ch.hoene.perzist.access.db.Database;
import ch.hoene.perzist.access.executor.OperationExecutor;
import ch.hoene.perzist.access.filter.Filter;
import ch.hoene.perzist.access.sort.FieldOrder;
import ch.hoene.perzist.access.sort.SortOrder;
import ch.hoene.perzist.android.CounterSqlite;
import ch.hoene.perzist.android.CreatorForListSqlite;
import ch.hoene.perzist.android.DeleteTableContentSqlLite;
import ch.hoene.perzist.android.InsertOperationSqlLite;
import ch.hoene.perzist.android.MappingDistinctSqlLite;
import ch.hoene.perzist.android.QuerySqlite;
import ch.hoene.perzist.source.relational.FieldPersistable;
import ch.hoene.perzist.source.relational.FieldSortOrder;
import ch.hoene.perzist.source.relational.View;
import ch.hoene.perzist.source.sql.query.Select;
import com.turtleplayer.model.Album;
import com.turtleplayer.model.AlbumArtLocation;
import com.turtleplayer.model.Artist;
import com.turtleplayer.model.FSobject;
import com.turtleplayer.model.Genre;
import com.turtleplayer.model.Song;
import com.turtleplayer.model.Track;
import com.turtleplayer.persistance.turtle.FileBase;
import com.turtleplayer.persistance.turtle.db.structure.Tables;
import com.turtleplayer.persistance.turtle.db.structure.Views;
import com.turtleplayer.persistance.turtle.mapping.AlbumArtLoactionToDbMapper;
import com.turtleplayer.persistance.turtle.mapping.AlbumCreator;
import com.turtleplayer.persistance.turtle.mapping.ArtistCreator;
import com.turtleplayer.persistance.turtle.mapping.DirCreator;
import com.turtleplayer.persistance.turtle.mapping.FsObjectToDbMapper;
import com.turtleplayer.persistance.turtle.mapping.GenreCreator;
import com.turtleplayer.persistance.turtle.mapping.SongCreator;
import com.turtleplayer.persistance.turtle.mapping.TrackCreator;
import com.turtleplayer.persistance.turtle.mapping.TrackToDbMapper;

import java.util.Arrays;
import java.util.List;



public class TurtleDatabase extends ObservableDatabase implements FileBase
{

	public TurtleDatabase(TurtleDatabaseImpl turtleDatabaseImpl)
	{
      super(turtleDatabaseImpl);
	}

	//Write------------------------------------

	/**
	 * @return true when successful inserted
	 */
	public boolean push(final Track track)
	{
    int insertedCount = push(track, new TrackToDbMapper());
		if(insertedCount > 0)
		{
			notifyUpdate(track);
			return true;
		}
		return false;
	}

	public void push(final AlbumArtLocation albumArtLocation)
	{
      push(albumArtLocation, new AlbumArtLoactionToDbMapper());
	}

	public void push(final FSobject dir)
	{
      push(dir, new FsObjectToDbMapper());
	}

	public void clear()
	{
		drop(Tables.TRACKS);
    drop(Tables.DIRS);
		notifyCleared();
	}

	//Read------------------------------------

	public boolean isEmpty(Filter<Tables.Tracks> filter)
	{
		return isEmpty(filter, Tables.TRACKS);
	}

	public int countAvailableTracks(Filter<Tables.Tracks> filter)
	{
      return count(filter, Tables.TRACKS);
	}

	public List<? extends Track> getTracks(Filter<? super Tables.Tracks> filter)
	{
		return getList(filter, new TrackCreator(), Tables.TRACKS, Tables.TRACKS, new FieldSortOrder(Tables.Tracks.TITLE, SortOrder.ASC));
	}

	public List<? extends Song> getSongs(Filter<? super Tables.Tracks> filter)
	{
		return getList(filter, new SongCreator(), Tables.TRACKS, Views.SONGS, new FieldSortOrder(Tables.SongsReadable.TITLE, SortOrder.ASC));
	}

	public List<? extends Artist> getArtists(Filter<? super Tables.Tracks> filter)
	{
		return getList(filter, new ArtistCreator(), Tables.TRACKS, Views.ARTISTS,  new FieldSortOrder(Tables.ArtistsReadable.ARTIST, SortOrder.ASC));
	}

	public List<? extends Genre> getGenres(Filter<? super Tables.Tracks> filter)
	{
		return getList(filter, new GenreCreator(), Tables.TRACKS, Views.GENRES, new FieldSortOrder(Tables.GenresReadable.GENRE, SortOrder.ASC));
	}

	public List<? extends Album> getAlbums(Filter<? super Tables.Tracks> filter)
	{
		return getList(filter, new AlbumCreator(), Tables.TRACKS, Views.ALBUMS, new FieldSortOrder(Tables.AlbumsReadable.ALBUM, SortOrder.ASC));
	}

	public List<? extends FSobject> getDirList(Filter<? super Tables.Dirs> filter)
	{
		return getList(filter, new DirCreator(), Tables.DIRS, Tables.DIRS, new FieldSortOrder(Tables.Dirs.NAME, SortOrder.ASC));
	}

}