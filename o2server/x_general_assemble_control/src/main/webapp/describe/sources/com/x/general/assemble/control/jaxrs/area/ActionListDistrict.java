package com.x.general.assemble.control.jaxrs.area;

import java.util.ArrayList;
import java.util.List;

import com.x.base.core.container.EntityManagerContainer;
import com.x.base.core.container.factory.EntityManagerContainerFactory;
import com.x.base.core.project.bean.WrapCopier;
import com.x.base.core.project.bean.WrapCopierFactory;
import com.x.base.core.project.cache.ApplicationCache;
import com.x.base.core.project.http.ActionResult;
import com.x.base.core.project.http.EffectivePerson;
import com.x.base.core.project.tools.ListTools;
import com.x.general.assemble.control.Business;
import com.x.general.core.entity.area.District;

import net.sf.ehcache.Element;

public class ActionListDistrict extends BaseAction {

	ActionResult<List<Wo>> execute(EffectivePerson effectivePerson, String province, String city) throws Exception {
		try (EntityManagerContainer emc = EntityManagerContainerFactory.instance().create()) {
			ActionResult<List<Wo>> result = new ActionResult<>();
			List<Wo> wos = new ArrayList<>();
			String cacheKey = ApplicationCache.concreteCacheKey(this.getClass(), province, city);
			Element element = cache.get(cacheKey);
			if ((null != element) && (null != element.getObjectValue())) {
				wos = (List<Wo>) element.getObjectValue();
			} else {
				Business business = new Business(emc);
				District districtProvince = this.getProvince(business, province);
				if (null == districtProvince) {
					throw new ExceptionDistrictNotExist(province);
				}
				District districtCity = this.getCity(business, districtProvince, city);
				if (null == districtCity) {
					throw new ExceptionDistrictNotExist(city);
				}
				wos = Wo.copier.copy(this.listDistrict(business, districtCity));
				cache.put(new Element(cacheKey, wos));
			}
			result.setData(wos);
			return result;
		}
	}

	public static class Wo extends District {

		private static final long serialVersionUID = -6068531258644538959L;
		static WrapCopier<District, Wo> copier = WrapCopierFactory.wo(District.class, Wo.class,
				ListTools.toList(District.zipCode_FIELDNAME, District.center_FIELDNAME, District.name_FIELDNAME,
						District.level_FIELDNAME),
				null);
	}

}
