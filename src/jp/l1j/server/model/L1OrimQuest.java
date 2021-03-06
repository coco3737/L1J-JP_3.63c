/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package jp.l1j.server.model;

import java.util.HashMap;
import java.util.logging.Logger;
import jp.l1j.server.model.etc.L1OrimQuestInstance;
import jp.l1j.server.model.map.L1InstanceMap;

public class L1OrimQuest{
	private static Logger _log = Logger.getLogger(L1OrimQuest.class.getName());
	
	private static HashMap<Integer, L1OrimQuestInstance> _activeMaps;
	
	private static L1OrimQuest _instance;

	public static L1OrimQuest getInstance() {
		if (_instance == null) {
			_instance = new L1OrimQuest();
		}
		return _instance;
	}
	
	L1OrimQuest(){
		_activeMaps= new HashMap<Integer, L1OrimQuestInstance>();
	}

	/**
	 * 指定された要素に含まれるインスタンスを返す
	 *
	 * @param x
	 * @return 格納されているインスタンス
	 */
	public L1OrimQuestInstance getActiveMaps(int mapId){
		if (!_activeMaps.containsKey( mapId)) {
			return null;
		}
		return _activeMaps.get(mapId);
	}
	
	/**
	 * インスタンスマップを生成し、HashMapにリンクを生成
	 * @param mapId コピー元のマップID
	 * @return
	 */
	public short setActiveMaps(int mapId){
		int instanceMapId= L1InstanceMap.getInstance().addInstanceMap(mapId);
		L1OrimQuestInstance obj=new L1OrimQuestInstance((short)instanceMapId);
		_activeMaps.put(instanceMapId,obj);
		return obj.getMapId();
	}
	
	/**
	 * クエスト終了につき生成したインスタンスマップを開放する
	 * @param mapId
	 */
	public void resetActiveMaps(int mapId){
		if (!_activeMaps.containsKey(mapId)) {
			return;
		}
		_activeMaps.remove(mapId);
		L1InstanceMap.getInstance().removeInstanceMap(mapId);
	}
	
	/**
	 * 進行中のクエストの件数を取得
	 * @return
	 */
	public int getNumOfActiveMaps(){
		return _activeMaps.size();
	}
}
