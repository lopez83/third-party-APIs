package com.lopez83.apis.serverlocator.model;

/**
 * * curl http://ipinfo.io/89.140.72.44 { "ip": "89.140.72.44", "hostname":
 * "dns1.dnsneolabels.com", "city": null, "region": null, "country": "ES",
 * "loc": "40.4000,0.0000", "org": "AS6739 Cableuropa - ONO" }
 */

public class IpInfoData {

	private String ip;
	private String hostname;
	private String city;
	private String region;
	private String country;
	private String loc;
	private String org;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	@Override
	public String toString() {
		return "IpInfoData [ip=" + ip + ", hostname=" + hostname + ", city="
				+ city + ", region=" + region + ", country=" + country
				+ ", loc=" + loc + ", org=" + org + "]";
	}

}
