package com.x.query.assemble.surface;

import com.hankcs.hanlp.model.crf.CRFLexicalAnalyzer;
import com.x.base.core.project.Context;
import com.x.base.core.project.config.Config;
import com.x.base.core.project.logger.LoggerFactory;

public class ThisApplication {

	protected static Context context;

	public static CRFLexicalAnalyzer analyzer;

	public static Context context() {
		return context;
	}

	public static void init() {
		try {
			LoggerFactory.setLevel(Config.logLevel().x_query_assemble_surface());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void destroy() {
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
