package turtle.player.persistance.turtle;

import turtle.player.model.*;
import turtle.player.persistance.framework.filter.Filter;
import turtle.player.persistance.turtle.db.structure.Tables;

import java.util.Collection;
import java.util.Set;

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

/**
 * @param <Q> eg sql as String
 */
public interface FileBase
{
	Collection<Track> getTracks(Filter<Tables.Tracks> filter);

	Collection<Album> getAlbums(Filter<Tables.Tracks> filter);

	Collection<Artist> getArtist(Filter<Tables.Tracks> filter);
}
