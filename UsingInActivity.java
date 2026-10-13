//Uri → byte[]

byte[] imageBlob = Class_Uri_To_Blob.uriToBlob(this, uri);

//byte[] → Bitmap

Bitmap bitmap = Class_Uri_To_Blob.blobToBitmap(imageBlob);

//byte[] → String

String base64 = Class_Uri_To_Blob.blobToBase64(imageBlob);

//String → byte[]

byte[] imageBlob = Class_Uri_To_Blob.base64ToBlob(base64);