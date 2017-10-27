package tuanpv.tool.utils;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import tuanpv.tool.Constant;
import tuanpv.tool.model.Action;
import tuanpv.tool.model.Profile;

public class XmlUtils {
	public static Options parseOptions(String classpathName) throws Exception {
		Options options = new Options();

		// load file XML configuration about the application profile
		InputStream stream = ClassLoader.getSystemResourceAsStream(classpathName);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(stream);

		// optional, but recommended
		document.getDocumentElement().normalize();

		// get list of option
		NodeList nodeList = document.getElementsByTagName(Constant.OPTION_NAME);
		for (int idx = 0; idx < nodeList.getLength(); idx++) {
			Element element = (Element) nodeList.item(idx);
			Option option = new Option(element.getAttribute(Constant.OPTION_ATTR_OPT),
					element.getAttribute(Constant.OPTION_ATTR_LONG_OPT),
					StringUtils.equals(Boolean.TRUE.toString(),
							element.getAttribute(Constant.OPTION_ATTR_HAS_ARGUMENT)),
					element.getAttribute(Constant.OPTION_ATTR_DESCRIPTION));
			option.setRequired(
					StringUtils.equals(Boolean.TRUE.toString(), element.getAttribute(Constant.OPTION_ATTR_REQUIRED)));
			options.addOption(option);
		}

		// close stream
		stream.close();

		return options;
	}

	public static List<Profile> parseProfile(String pathName) throws Exception {
		List<Profile> result = new ArrayList<>();

		// load file XML configuration about the application profile
		File file = new File(pathName);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(file);

		// optional, but recommended
		document.getDocumentElement().normalize();

		NodeList nodeList = document.getElementsByTagName(Constant.PROFILE_NAME);
		for (int idx = 0; idx < nodeList.getLength(); idx++) {
			Element element = (Element) nodeList.item(idx);
			Profile profile = new Profile(element.getAttribute(Constant.PROFILE_ATTR_ID));

			// process action list
			NodeList acts = element.getElementsByTagName(Constant.PROFILE_ACTION_NAME);
			for (int adx = 0; adx < acts.getLength(); adx++) {
				Element bean = (Element) acts.item(adx);
				Action action = new Action();

				// put BEAN and CLAZZ to ACTION
				if (bean.hasAttribute(Constant.PROFILE_ACTION_ATTR_BEAN))
					action.bean = bean.getAttribute(Constant.PROFILE_ACTION_ATTR_BEAN);
				if (bean.hasAttribute(Constant.PROFILE_ACTION_ATTR_CLASS))
					action.clazz = bean.getAttribute(Constant.PROFILE_ACTION_ATTR_CLASS);

				// get a map containing the attributes of this node
				NamedNodeMap attributes = bean.getAttributes();
				for (int attrIdx = 0; attrIdx < attributes.getLength(); attrIdx++) {
					Attr attr = (Attr) attributes.item(attrIdx);
					if (StringUtils.equals(attr.getNodeName(), Constant.PROFILE_ACTION_ATTR_BEAN)
							|| StringUtils.equals(attr.getNodeName(), Constant.PROFILE_ACTION_ATTR_CLASS))
						continue;

					action.addAttr(AppUtils.keyOfSubAction(attr.getNodeName()), attr.getNodeValue());
				}

				profile.addAction(action);
			}

			result.add(profile);
		}

		return result;
	}

	public static void store2File(String path, String rootName, Object bean) throws Exception {
		System.out.println("-----------------");
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		// root elements
		Document doc = docBuilder.newDocument();

		// append next node
		appendNode(doc, doc, rootName, bean, false);

		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);

		// Output to file
		StreamResult result = new StreamResult(new File(path));

		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);

		// transform content
		transformer.transform(source, result);
	}

	@SuppressWarnings("unchecked")
	private static void appendNode(Document document, Object parent, String rootName, Object bean, boolean isList)
			throws IllegalAccessException {
		Element element = null;
		if (bean instanceof List) {
			element = document.createElement(rootName);
			appendChild(parent, element);

			List<Object> list = (List<Object>) bean;
			for (Object object : list) {
				appendNode(document, element, object.getClass().getSimpleName(), object, true);
			}

			return;
		}

		if (bean instanceof Map) {
			Map<?, ?> beanMap = (Map<?, ?>) bean;
			for (Object key : beanMap.keySet())
				((Element) parent).setAttribute(key.toString(), beanMap.get(key).toString());

			return;
		}

		if (bean instanceof String) {
			element = document.createElement(isList ? "item" : rootName);
			element.appendChild(document.createTextNode(bean.toString()));
			appendChild(parent, element);

			return;
		}

		if (bean instanceof Object) {
			element = document.createElement(StringUtils.uncapitalize(rootName));
			appendChild(parent, element);

			for (Field field : bean.getClass().getFields())
				appendNode(document, element, field.getName(), FieldUtils.readField(field, bean), false);

			return;
		}
	}

	private static void appendChild(Object parent, Element element) {
		if (parent instanceof Document)
			((Document) parent).appendChild(element);
		else
			((Element) parent).appendChild(element);
	}
}
