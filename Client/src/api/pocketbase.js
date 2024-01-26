import Pocketbase from "pocketbase";
import { DB_URL } from "./constance";

const pb = new Pocketbase(DB_URL);
pb.autoCancellation(false);

export default pb;
