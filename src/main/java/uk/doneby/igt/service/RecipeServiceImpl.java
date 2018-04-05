package uk.doneby.igt.service;

import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl implements RecipeService {

	@Autowired
	DataSource dataSource;
	
	@Override
	@Transactional
	public boolean like(Long recipeId, Long userId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);		
		Object [] params = new Object[] { recipeId, userId };

		int count = jdbcTemplate.queryForObject("SELECT count(*) FROM RECIPE_LIKES WHERE RECIPE_ID = ? AND USER_ID = ?", params, Integer.class);
		if (count > 0) {
			int rows = jdbcTemplate.update("DELETE FROM RECIPE_LIKES WHERE RECIPE_ID = ? AND USER_ID = ?", params, Integer.class);
			System.out.println(rows + " Likes(s) deleted.");
			return false;
		} else {
			jdbcTemplate.update("INSERT INTO RECIPE_LIKES (RECIPE_ID, USER_ID) VALUES(?, ?)", params);
			return true;
		}

	}

}
