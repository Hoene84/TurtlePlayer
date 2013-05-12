package turtle.player.playlist.playorder;

import turtle.player.model.Track;
import turtle.player.persistance.framework.sort.FieldOrder;
import turtle.player.persistance.framework.sort.Order;
import turtle.player.persistance.framework.sort.OrderSet;
import turtle.player.persistance.framework.sort.SortOrder;
import turtle.player.persistance.turtle.db.structure.Tables;

/**
 * TURTLE PLAYER
 * <p/>
 * Licensed under MIT & GPL
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 * <p/>
 * More Information @ www.turtle-player.co.uk
 *
 * @author Simon Honegger (Hoene84)
 */

public class DefaultOrder extends OrderSet<Tables.Tracks>
{
	public DefaultOrder(SortOrder sortOrder)
	{
		super(new FieldOrder<Tables.Tracks, Track, String>(Tables.Tracks.ARTIST, sortOrder),
				  new FieldOrder<Tables.Tracks, Track, String>(Tables.Tracks.ALBUM, sortOrder),
				  new FieldOrder<Tables.Tracks, Track, Integer>(Tables.Tracks.NUMBER, sortOrder),
				  new FieldOrder<Tables.Tracks, Track, String>(Tables.Tracks.TITLE, sortOrder)
		);
	}
}
