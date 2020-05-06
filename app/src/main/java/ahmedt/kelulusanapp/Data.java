package ahmedt.kelulusanapp;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("keterangan")
	private String keterangan;

	@SerializedName("file")
	private String file;

	@SerializedName("nama")
	private String nama;

	@SerializedName("nisn")
	private String nisn;

	@SerializedName("jurusan")
	private String jurusan;

	public void setKeterangan(String keterangan){
		this.keterangan = keterangan;
	}

	public String getKeterangan(){
		return keterangan;
	}

	public void setFile(String file){
		this.file = file;
	}

	public String getFile(){
		return file;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setNisn(String nisn){
		this.nisn = nisn;
	}

	public String getNisn(){
		return nisn;
	}

	public void setJurusan(String jurusan){
		this.jurusan = jurusan;
	}

	public String getJurusan(){
		return jurusan;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"keterangan = '" + keterangan + '\'' + 
			",file = '" + file + '\'' + 
			",nama = '" + nama + '\'' + 
			",nisn = '" + nisn + '\'' + 
			",jurusan = '" + jurusan + '\'' + 
			"}";
		}
}