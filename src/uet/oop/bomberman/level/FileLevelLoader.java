package uet.oop.bomberman.level;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Balloon;
import uet.oop.bomberman.entities.character.enemy.Oneal;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
import uet.oop.bomberman.entities.tile.item.FlameItem;
import uet.oop.bomberman.entities.tile.item.BombItem;
import uet.oop.bomberman.exceptions.LoadLevelException;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

import java.io.*;
public class FileLevelLoader extends LevelLoader {

	/**
	 * Ma trận chứa thông tin bản đồ, mỗi phần tử lưu giá trị kí tự đọc được
	 * từ ma trận bản đồ trong tệp cấu hình
	 */
	private static char[][] _map;
	
	public FileLevelLoader(Board board, int level) throws LoadLevelException {
		super(board, level);
	}
	
	@Override
	public void loadLevel(int level) {
		// TODO: đọc dữ liệu từ tệp cấu hình /levels/Level{level}.txt
		// TODO: cập nhật các giá trị đọc được vào _width, _height, _level, _map
		try{
			InputStream input = FileLevelLoader.class.getResourceAsStream("/levels/level" + level + ".txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(input));
			String data = br.readLine();
			String phantu[] = data.split(" ");
			_level = Integer.parseInt(phantu[0]);
			_height = Integer.parseInt(phantu[1]);
			_width = Integer.parseInt(phantu[2]);
			_map = new char[_height][_width];
			String line ;
			for(int i = 0; i < _height; i++)
            {
                line = br.readLine();
                for(int j = 0; j < _width; j++)
                    _map[i][j] = line.charAt(j);
            }
		}
		catch(IOException e)
        {
            e.printStackTrace();
        }
	}

	@Override
	public void createEntities() {
		// TODO: tạo các Entity của màn chơi
		// TODO: sau khi tạo xong, gọi _board.addEntity() để thêm Entity vào game

		// TODO: phần code mẫu ở dưới để hướng dẫn cách thêm các loại Entity vào game
		// TODO: hãy xóa nó khi hoàn thành chức năng load màn chơi từ tệp cấu hình
		// thêm Wall
		for (int x = 0; x < 20; x++) {
			for (int y = 0; y < 20; y++) {
				int pos = x + y * _width;
				Sprite sprite = y == 0 || x == 0 || x == 10 || y == 10 ? Sprite.wall : Sprite.grass;
				_board.addEntity(pos, new Grass(x, y, sprite));
			}
		}

		// thêm Bomber
		int xBomber = 1, yBomber = 1;
		_board.addCharacter( new Bomber(Coordinates.tileToPixel(xBomber), Coordinates.tileToPixel(yBomber) + Game.TILES_SIZE, _board) );
		Screen.setOffset(0, 0);
		_board.addEntity(xBomber + yBomber * _width, new Grass(xBomber, yBomber, Sprite.grass));

		// thêm Enemy
		int xE = 2, yE = 1;
		_board.addCharacter( new Balloon(Coordinates.tileToPixel(xE), Coordinates.tileToPixel(yE) + Game.TILES_SIZE, _board));
		_board.addEntity(xE + yE * _width, new Grass(xE, yE, Sprite.grass));

		// thêm Brick
		int xB = 3, yB = 1;
		_board.addEntity(xB + yB * _width,
				new LayeredEntity(xB, yB,
					new Grass(xB, yB, Sprite.grass),
					new Brick(xB, yB, Sprite.brick)
				)
		);

		// thêm Item kèm Brick che phủ ở trên
		int xI = 1, yI = 2;
		_board.addEntity(xI + yI * _width,
				new LayeredEntity(xI, yI,
					new Grass(xI ,yI, Sprite.grass),
					new SpeedItem(xI, yI, Sprite.powerup_flames),
					new Brick(xI, yI, Sprite.brick)
				)
		);
	}

}
